package com.example.ahmed.wasetco.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ahmed.wasetco.R;
import com.example.ahmed.wasetco.data.Constants;
import com.example.ahmed.wasetco.data.models.AgentModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgentsAdapter extends RecyclerView.Adapter<AgentsAdapter.AgentViewHolder> {

    private OnItemClickListener listener;

    List<AgentModel> agents;
    Context mContext;

    public AgentsAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public AgentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_agents_list, parent, false);
        AgentViewHolder viewHolder = new AgentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AgentViewHolder holder, int position) {


        if (agents != null) {
            AgentModel model = agents.get(position);
            holder.tvAgentName.setText(model.getName());

            if (!model.getImageIcon().equals("null")) {
                Glide.with(mContext).load(Constants.IMAGE_URL_MEMBERS + model.getImageIcon() + "-s.jpg").into(holder.ivAgent);
            }

        }
    }

    @Override
    public int getItemCount() {
        if (agents != null) {
            return agents.size();
        } else {
            return 0;
        }
    }

    public void setAgents(List<AgentModel> agents) {
        this.agents = agents;
        notifyDataSetChanged();
    }

    public class AgentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivAgent)
        ImageView ivAgent;

        @BindView(R.id.tvAgentName)
        TextView tvAgentName;

        @BindView(R.id.imBtnFacebook)
        ImageButton imFacebook;

        @BindView(R.id.imBtnTwitter)
        ImageButton imTwitter;

        @BindView(R.id.imBtnGoogle)
        ImageButton imGoogle;

        @BindView(R.id.imBtnLinkeIn)
        ImageButton imLinkedIn;

        @BindView(R.id.linearAgentDetails)
        LinearLayout linearAgentDetails;


        public AgentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(agents.get(position));
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(AgentModel agentModel);
    }
}
