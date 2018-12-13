package com.example.ahmed.wasetco.ui.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmed.wasetco.R;
import com.example.ahmed.wasetco.data.models.RealEstateSaleModel;
import com.example.ahmed.wasetco.ui.adapters.RealEstateSaleAdapter;
import com.example.ahmed.wasetco.viewmodels.RealEstatSaleViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RealEstateRentFragment extends Fragment {
    @BindView(R.id.rvRealestates)
    RecyclerView rvRealEstates;

    RealEstatSaleViewModel viewModel;

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
        final RealEstateSaleAdapter adapter = new RealEstateSaleAdapter(getActivity());
        rvRealEstates.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRealEstates.setAdapter(adapter);

        viewModel.setType("rent");

        viewModel.getRealEstateSale().observe(getActivity(), new Observer<ArrayList<RealEstateSaleModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<RealEstateSaleModel> realEstateSaleModels) {
                adapter.setRealEstatesSale(realEstateSaleModels);
            }
        });
    }
}
