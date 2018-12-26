package com.example.ahmed.wasetco.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ahmed.wasetco.R;
import com.example.ahmed.wasetco.data.Constants;
import com.example.ahmed.wasetco.data.models.RealEstateFeaturedModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RealStateFeaturedAdapter extends RecyclerView.Adapter<RealStateFeaturedAdapter.RealEstatViewHolder> {

    private OnItemClickListener listener;

    List<RealEstateFeaturedModel> realEstatesFeatured;
    Context mContext;

    public RealStateFeaturedAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RealEstatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_custom_realestates_list, parent, false);
        RealEstatViewHolder viewHolder = new RealEstatViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RealEstatViewHolder holder, int position) {

        if (realEstatesFeatured != null) {
            RealEstateFeaturedModel model = realEstatesFeatured.get(position);
            holder.tvTitle.setText(model.getPropertyName());
            holder.tvAddress.setText(model.getAddress());
            holder.tvDescription.setText(model.getDescription());
            holder.tvBathroom.setText(model.getBathrooms());
            holder.tvBedroom.setText(model.getBedrooms());
            holder.tvLandArea.setText(model.getLandArea());
            holder.tvPrice.setText("LE " + model.getPrice());
            Glide.with(mContext).load(Constants.IMAGE_URL + model.getFeaturedImage() + "-s.jpg").into(holder.ivRealEstate);

        }
    }

    @Override
    public int getItemCount() {
        if (realEstatesFeatured != null) {
            return realEstatesFeatured.size();
        } else {
            return 0;
        }
    }

    public void setRealEstatesFeatred(List<RealEstateFeaturedModel> realEstatesFeatured) {
        this.realEstatesFeatured = realEstatesFeatured;
        notifyDataSetChanged();
    }

    public class RealEstatViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivRealEstat)
        ImageView ivRealEstate;

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvAdress)
        TextView tvAddress;

        @BindView(R.id.tvDescription)
        TextView tvDescription;

        @BindView(R.id.tvLandArea)
        TextView tvLandArea;

        @BindView(R.id.tvBedroom)
        TextView tvBedroom;

        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvBathroom)
        TextView tvBathroom;

        public RealEstatViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(realEstatesFeatured.get(position));
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(RealEstateFeaturedModel realEstateFeaturedModel);
    }
}
