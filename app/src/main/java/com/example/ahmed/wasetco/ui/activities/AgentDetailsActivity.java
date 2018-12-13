package com.example.ahmed.wasetco.ui.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ahmed.wasetco.R;
import com.example.ahmed.wasetco.data.Constants;
import com.example.ahmed.wasetco.data.models.RealEstateModel;
import com.example.ahmed.wasetco.events.EventAgents;
import com.example.ahmed.wasetco.ui.adapters.RealEstatesAdapter;
import com.example.ahmed.wasetco.viewmodels.RealEstateViewModel;

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

    RealEstateViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_details);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(RealEstateViewModel.class);
        final RealEstatesAdapter adapter = new RealEstatesAdapter(this);
        rvAgentsRealEstate.setLayoutManager(new LinearLayoutManager(this));
        rvAgentsRealEstate.setAdapter(adapter);

        viewModel.getRealEstates().observe(this, new Observer<ArrayList<RealEstateModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<RealEstateModel> realEstateModels) {
                adapter.setRealEstates(realEstateModels);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(EventAgents event) {

        Toast.makeText(this, "label " + event.getAgentModel().toString(),
                Toast.LENGTH_SHORT).show();
        EventBus.getDefault().removeStickyEvent(event.toString()); // don't forget to remove the sticky event if youre done with it

        tvAgentName.setText(event.getAgentModel().getName());
        Glide.with(this).load(Constants.IMAGE_URL_MEMBERS + event.getAgentModel().getImageIcon() + "-s.jpg").into(ivAgent);
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
