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
import android.widget.Toast;

import com.example.ahmed.wasetco.R;
import com.example.ahmed.wasetco.data.models.RealEstateFeaturedModel;
import com.example.ahmed.wasetco.events.EventUrl;
import com.example.ahmed.wasetco.ui.activities.FilterActivity;
import com.example.ahmed.wasetco.ui.activities.RealEstateDetailsActivity;
import com.example.ahmed.wasetco.ui.adapters.RealStateFeaturedAdapter;
import com.example.ahmed.wasetco.viewmodels.RealEstateFeaturedViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FilterResultFragment extends Fragment {

    @BindView(R.id.rvFeaturedRealestates)
    RecyclerView rv;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ArrayList<RealEstateFeaturedModel> realEstateFeaturedList;
    private RealStateFeaturedAdapter adapter;

    RealEstateFeaturedViewModel viewModel;

    public FilterResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_result, container, false);
        FilterActivity.toolbar.setTitle(R.string.result);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(EventUrl event) {
        Toast.makeText(getActivity(), event.getUrl(), Toast.LENGTH_SHORT).show();
        viewModel = ViewModelProviders.of(getActivity()).get(RealEstateFeaturedViewModel.class);
        realEstateFeaturedList = new ArrayList<>();
        adapter = new RealStateFeaturedAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);
        adapter.setRealEstatesFeatred(realEstateFeaturedList);

        loadDataFromApi(event.getUrl());

        adapter.setOnItemClickListener(realEstateModel -> {
            startActivity(new Intent(getActivity(), RealEstateDetailsActivity.class));
        });
        EventBus.getDefault().removeStickyEvent(event.toString()); // don't forget to remove the sticky event if youre done with it
    }

    public void loadDataFromApi(String url) {

        viewModel.getFilter(url).observe(getActivity(), realEstateFeaturedModels -> {
            try {
                progressBar.setVisibility(View.GONE);
                realEstateFeaturedList.addAll(realEstateFeaturedModels);
                Log.v("ListSize", "All " + String.valueOf(realEstateFeaturedList.size()));
                Log.v("ListSize", "Real " + String.valueOf(realEstateFeaturedModels.size()));
                if (realEstateFeaturedModels != null)
                    adapter.setRealEstatesFeatred(realEstateFeaturedModels);
            } catch (Exception e) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }
}
