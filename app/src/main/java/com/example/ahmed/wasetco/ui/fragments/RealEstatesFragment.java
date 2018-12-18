package com.example.ahmed.wasetco.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ahmed.wasetco.R;
import com.example.ahmed.wasetco.data.models.RealEstateModel;
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
                Toast.makeText(getActivity(), "Page " + page, Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`

        viewModel.getRealEstates(offset).observe(getActivity(), realEstateModels -> {
            try {

                realEstateList.addAll(realEstateModels);
                Log.v("ListSize", "All " + String.valueOf(realEstateList.size()));
                Log.v("ListSize", "Real " + String.valueOf(realEstateModels.size()));

                Toast.makeText(getActivity(), "ListSize" + String.valueOf(realEstateModels.size()), Toast.LENGTH_SHORT).show();

                adapter.notifyItemRangeInserted(adapter.getItemCount(), realEstateList.size() - 1);

            } catch (Exception e) {

            }
        });
    }

}
