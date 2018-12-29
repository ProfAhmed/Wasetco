package com.example.ahmed.wasetco.ui.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ahmed.wasetco.R;
import com.example.ahmed.wasetco.data.Constants;
import com.example.ahmed.wasetco.data.models.RealEstateFeaturedModel;
import com.example.ahmed.wasetco.events.EventAgents;
import com.example.ahmed.wasetco.ui.adapters.RealStateFeaturedAdapter;
import com.example.ahmed.wasetco.viewmodels.AgentRealEstatesViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgentDetailsActivity extends AppCompatActivity {

    @BindView(R.id.ivAgentDetails)
    ImageView ivAgent;

    @BindView(R.id.tvAgentNameDetail)
    TextView tvAgentName;

    @BindView(R.id.imBtnFacebook)
    ImageButton imFacebook;

    @BindView(R.id.imBtnTwitter)
    ImageButton imTwitter;

    @BindView(R.id.imBtnGoogle)
    ImageButton imGoogle;

    @BindView(R.id.imBtnLinkeIn)
    ImageButton imLinkedIn;

    @BindView(R.id.rvAgentsRealestates)
    RecyclerView rvAgentsRealEstate;

    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    AgentRealEstatesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_details);
        ButterKnife.bind(this);
        rvAgentsRealEstate.setNestedScrollingEnabled(false);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewModel = ViewModelProviders.of(this).get(AgentRealEstatesViewModel.class);
        final RealStateFeaturedAdapter adapter = new RealStateFeaturedAdapter(this);
        rvAgentsRealEstate.setLayoutManager(new LinearLayoutManager(this));
        rvAgentsRealEstate.setAdapter(adapter);

        viewModel.getAgentRealEstates().observe(this, new Observer<ArrayList<RealEstateFeaturedModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<RealEstateFeaturedModel> realEstateModels) {
                progressBar.setVisibility(View.GONE);
                adapter.setRealEstatesFeatred(realEstateModels);
            }
        });

        adapter.setOnItemClickListener(realEstateModel -> {

            startActivity(new Intent(this, RealEstateDetailsActivity.class));

        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(EventAgents event) {

        viewModel.setAgentId(String.valueOf(event.getAgentModel().getId()));

        Toast.makeText(this, "label " + event.getAgentModel().getId(),
                Toast.LENGTH_SHORT).show();
        EventBus.getDefault().removeStickyEvent(event.toString()); // don't forget to remove the sticky event if youre done with it

        tvAgentName.setText(event.getAgentModel().getName());
        if (!event.getAgentModel().getImageIcon().equals("null")) {
            Glide.with(this).load(Constants.IMAGE_URL_MEMBERS + event.getAgentModel().getImageIcon() + "-s.jpg").into(ivAgent);
        }
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
