package com.example.ahmed.wasetco.ui.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.example.ahmed.wasetco.data.models.AgentModel;
import com.example.ahmed.wasetco.events.EventAgents;
import com.example.ahmed.wasetco.ui.activities.AgentDetailsActivity;
import com.example.ahmed.wasetco.ui.adapters.AgentsAdapter;
import com.example.ahmed.wasetco.viewmodels.AgentViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgentsFragment extends Fragment {

    @BindView(R.id.rvAgents)
    RecyclerView rvAgents;

    AgentViewModel viewModel;

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
        final AgentsAdapter adapter = new AgentsAdapter(getActivity());
        rvAgents.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAgents.setAdapter(adapter);

        viewModel.getAgent().observe(getActivity(), new Observer<ArrayList<AgentModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<AgentModel> agentModels) {
                adapter.setAgents(agentModels);
            }
        });

        adapter.setOnItemClickListener(agentModel -> {
            EventAgents event = new EventAgents(agentModel);
            EventBus.getDefault().postSticky(event);
            startActivity(new Intent(getActivity(), AgentDetailsActivity.class));

        });
    }
}
