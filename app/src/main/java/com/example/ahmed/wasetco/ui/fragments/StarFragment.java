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
import com.example.ahmed.wasetco.data.models.RealEstateFeaturedModel;
import com.example.ahmed.wasetco.ui.adapters.RealStateFeaturedAdapter;
import com.example.ahmed.wasetco.viewmodels.RealEstateFeaturedViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StarFragment extends Fragment {

    @BindView(R.id.rvStarRealestates)
    RecyclerView rvStarRealEstates;

    RealEstateFeaturedViewModel viewModel;

    public StarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_star, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(RealEstateFeaturedViewModel.class);
        RealStateFeaturedAdapter adapter = new RealStateFeaturedAdapter(getActivity());
        rvStarRealEstates.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvStarRealEstates.setAdapter(adapter);

        viewModel.getFeaturedRealEstates().observe(getActivity(), new Observer<ArrayList<RealEstateFeaturedModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<RealEstateFeaturedModel> realEstateFeaturedModels) {

                adapter.setRealEstatesFeatred(realEstateFeaturedModels);
            }
        });
    }
}
