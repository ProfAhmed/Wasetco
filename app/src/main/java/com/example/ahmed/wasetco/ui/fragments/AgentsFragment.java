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
import com.example.ahmed.wasetco.data.models.AgentModel;
import com.example.ahmed.wasetco.events.EventAgents;
import com.example.ahmed.wasetco.ui.activities.AgentDetailsActivity;
import com.example.ahmed.wasetco.ui.adapters.AgentsAdapter;
import com.example.ahmed.wasetco.ui.adapters.EndlessRecyclerViewScrollListener;
import com.example.ahmed.wasetco.viewmodels.AgentViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgentsFragment extends Fragment {

    @BindView(R.id.rvAgents)
    RecyclerView rvAgents;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    AgentViewModel viewModel;
    private ArrayList<AgentModel> agentsList;
    private AgentsAdapter adapter;

    public AgentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agents, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(AgentViewModel.class);
        agentsList = new ArrayList<>();
        adapter = new AgentsAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvAgents.setLayoutManager(linearLayoutManager);
        rvAgents.setAdapter(adapter);
        adapter.setAgents(agentsList);

        //first loading
        loadNextDataFromApi(1);

        rvAgents.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
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

        adapter.setOnItemClickListener(agentModel -> {
            EventAgents event = new EventAgents(agentModel);
            EventBus.getDefault().postSticky(event);
            startActivity(new Intent(getActivity(), AgentDetailsActivity.class));

        });
    }

    public void loadNextDataFromApi(int offset) {

        viewModel.getAgent(offset).observe(getActivity(), realEstateModels -> {
            try {

                progressBar.setVisibility(View.GONE);
                agentsList.addAll(realEstateModels);
                Log.v("ListSize", "All " + String.valueOf(agentsList.size()));
                Log.v("ListSize", "Real " + String.valueOf(realEstateModels.size()));

                if (agentsList.size() > 1) {
                    adapter.notifyItemRangeInserted(adapter.getItemCount(), agentsList.size() - 1);
                } else {
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {

            }
        });
    }
}
