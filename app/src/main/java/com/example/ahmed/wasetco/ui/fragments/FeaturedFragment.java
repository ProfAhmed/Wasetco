package com.example.ahmed.wasetco.ui.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.ahmed.wasetco.data.models.RealEstateFeaturedModel;
import com.example.ahmed.wasetco.ui.activities.RealEstateDetailsActivity;
import com.example.ahmed.wasetco.ui.adapters.EndlessRecyclerViewScrollListener;
import com.example.ahmed.wasetco.ui.adapters.RealStateFeaturedAdapter;
import com.example.ahmed.wasetco.viewmodels.RealEstateFeaturedViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FeaturedFragment extends Fragment {

    @BindView(R.id.rvFeaturedRealestates)
    RecyclerView rvStarRealEstates;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    RealEstateFeaturedViewModel viewModel;
    private ArrayList<RealEstateFeaturedModel> realEstateFeaturedList;
    private RealStateFeaturedAdapter adapter;

    public FeaturedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_featured, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(RealEstateFeaturedViewModel.class);
        realEstateFeaturedList = new ArrayList<>();
        adapter = new RealStateFeaturedAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvStarRealEstates.setLayoutManager(linearLayoutManager);
        rvStarRealEstates.setAdapter(adapter);
        adapter.setRealEstatesFeatred(realEstateFeaturedList);

        //first loading
        loadNextDataFromApi(1);

        rvStarRealEstates.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
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

        viewModel.getFeaturedRealEstates(offset).observe(getActivity(), realEstateFeaturedModels -> {
            try {

                progressBar.setVisibility(View.GONE);
                realEstateFeaturedList.addAll(realEstateFeaturedModels);
                Log.v("ListSize", "All " + String.valueOf(realEstateFeaturedList.size()));
                Log.v("ListSize", "Real " + String.valueOf(realEstateFeaturedModels.size()));


                if (realEstateFeaturedList.size() > 1) {
                    adapter.notifyItemRangeInserted(adapter.getItemCount(), realEstateFeaturedList.size() - 1);
                } else {
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {

            }
        });
    }
}
