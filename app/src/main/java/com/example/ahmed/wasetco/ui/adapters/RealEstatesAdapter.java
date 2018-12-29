package com.example.ahmed.wasetco.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ahmed.wasetco.R;
import com.example.ahmed.wasetco.data.Constants;
import com.example.ahmed.wasetco.data.models.RealEstateModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RealEstatesAdapter extends RecyclerView.Adapter<RealEstatesAdapter.RealEstatViewHolder> {

    private OnItemClickListener listener;

    List<RealEstateModel> realEstates;
    Context mContext;
    int count = 0;

    public RealEstatesAdapter(Context context) {
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

        int viewType = getItemViewType(position);
        if (viewType == 1) {
            holder.ivAds.setVisibility(View.VISIBLE);
            Log.v("CountP", String.valueOf(position));
        }

        if (realEstates != null) {
            RealEstateModel model = realEstates.get(position);
            holder.tvTitle.setText(model.getPropertyTitle());
            holder.tvAddress.setText(model.getAddress());
            holder.tvDescription.setText(model.getDescription());
            holder.tvBathroom.setText(model.getBathrooms());
            holder.tvBedroom.setText(model.getBedrooms());
            holder.tvLandArea.setText(model.getLandArea());
            holder.tvPrice.setText("LE " + model.getPrice());
            Glide.with(mContext).load(Constants.IMAGE_URL + model.getFeaturedImage() + "-b.jpg").into(holder.ivRealEstate);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if ((position) % 5 == 0 && position > 4) {
            return 1;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (realEstates != null) {
            return realEstates.size();
        } else {
            return 0;
        }
    }

    public void setRealEstates(List<RealEstateModel> realEstates) {
        this.realEstates = realEstates;

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
        @BindView(R.id.ivAds)
        ImageView ivAds;
        @BindView(R.id.cv)
        CardView cv;

        public RealEstatViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            cv.setOnClickListener(view -> {
                int position = getAdapterPosition();
                Log.v("Listener", "Heyyy");
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(realEstates.get(position));
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(RealEstateModel realEstateModel);
    }
}
