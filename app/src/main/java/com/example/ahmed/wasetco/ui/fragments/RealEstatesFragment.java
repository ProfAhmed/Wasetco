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
import com.example.ahmed.wasetco.data.models.RealEstateModel;
import com.example.ahmed.wasetco.ui.adapters.RealEstatesAdapter;
import com.example.ahmed.wasetco.viewmodels.RealEstateViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RealEstatesFragment extends Fragment {

    @BindView(R.id.rvRealestates)
    RecyclerView rvRealEstates;

    RealEstateViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_real_estates, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(RealEstateViewModel.class);
        final RealEstatesAdapter adapter = new RealEstatesAdapter(getActivity());
        rvRealEstates.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRealEstates.setAdapter(adapter);


        viewModel.getRealEstates().observe(getActivity(), new Observer<ArrayList<RealEstateModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<RealEstateModel> realEstateModels) {
                adapter.setRealEstates(realEstateModels);

            }
        });
    }
}
