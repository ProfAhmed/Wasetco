package com.example.ahmed.wasetco.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ahmed.wasetco.R;
import com.example.ahmed.wasetco.data.models.RealEstateModel;
import com.example.ahmed.wasetco.ui.activities.RealEstateDetailsActivity;
import com.example.ahmed.wasetco.ui.adapters.EndlessRecyclerViewScrollListener;
import com.example.ahmed.wasetco.ui.adapters.RealEstatesAdapter;
import com.example.ahmed.wasetco.viewmodels.RealEstateViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RealEstatesFragment extends Fragment {

    @BindView(R.id.rvRealestates)
    RecyclerView rvRealEstates;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    RealEstateViewModel viewModel;

    RealEstatesAdapter adapter;

    List<RealEstateModel> realEstateList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_real_estates, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(RealEstateViewModel.class);
        realEstateList = new ArrayList<>();
        adapter = new RealEstatesAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvRealEstates.setLayoutManager(linearLayoutManager);
        rvRealEstates.setAdapter(adapter);
        adapter.setRealEstates(realEstateList);

        //first loading
        loadNextDataFromApi(1);

        rvRealEstates.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                loadNextDataFromApi(page);

                Log.v("ListSize", "Scroll " + page);

            }

            @Override
            public void show() {

            }

            @Override
            public void hide() {

            }
        });

        adapter.setOnItemClickListener(realEstateModel -> {

            startActivity(new Intent(getActivity(), RealEstateDetailsActivity.class));

        });

    }

    public void loadNextDataFromApi(int offset) {

        viewModel.getRealEstates(offset).observe(getActivity(), realEstateModels -> {
            try {

                progressBar.setVisibility(View.GONE);
                realEstateList.addAll(realEstateModels);
                Log.v("ListSize", "All " + String.valueOf(realEstateList.size()));
                Log.v("ListSize", "Real " + String.valueOf(realEstateModels.size()));


                if (realEstateList.size() > 1) {
                    adapter.notifyItemRangeInserted(adapter.getItemCount(), realEstateList.size() - 1);
                } else {
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {

            }
        });
    }

}
