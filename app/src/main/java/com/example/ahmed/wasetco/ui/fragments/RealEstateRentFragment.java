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
import com.example.ahmed.wasetco.data.models.RealEstateSaleModel;
import com.example.ahmed.wasetco.data.models.RequestType;
import com.example.ahmed.wasetco.ui.activities.RealEstateDetailsActivity;
import com.example.ahmed.wasetco.ui.adapters.EndlessRecyclerViewScrollListener;
import com.example.ahmed.wasetco.ui.adapters.RealEstateSaleAdapter;
import com.example.ahmed.wasetco.viewmodels.RealEstatSaleViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RealEstateRentFragment extends Fragment {
    @BindView(R.id.rvRealestates)
    RecyclerView rvRealEstates;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    RealEstatSaleViewModel viewModel;
    private RealEstateSaleAdapter adapter;
    private ArrayList<RealEstateSaleModel> realEstateRentList;

    public RealEstateRentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(RealEstatSaleViewModel.class);
        realEstateRentList = new ArrayList<>();
        adapter = new RealEstateSaleAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        rvRealEstates.setLayoutManager(linearLayoutManager);
        rvRealEstates.setAdapter(adapter);
        adapter.setRealEstatesSale(realEstateRentList);

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

        RequestType type = new RequestType(offset, "rent");
        viewModel.getRealEstateSale(type).observe(getActivity(), realEstateModels -> {
            try {

                progressBar.setVisibility(View.GONE);
                realEstateRentList.addAll(realEstateModels);
                Log.v("ListSize", "All " + String.valueOf(realEstateRentList.size()));
                Log.v("ListSize", "Real " + String.valueOf(realEstateModels.size()));

                if (realEstateRentList.size() > 1) {
                    adapter.notifyItemRangeInserted(adapter.getItemCount(), realEstateRentList.size() - 1);
                } else {
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {

            }
        });
    }

}
