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
import com.example.ahmed.wasetco.data.models.RealEstateSaleModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RealEstateSaleAdapter extends RecyclerView.Adapter<RealEstateSaleAdapter.SaleViewHolder> {

    private OnItemClickListener listener;

    List<RealEstateSaleModel> realEstateSaleModels;
    Context mContext;

    public RealEstateSaleAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public SaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_custom_realestates_list, parent, false);
        SaleViewHolder viewHolder = new SaleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SaleViewHolder holder, int position) {

        if (realEstateSaleModels != null) {
            RealEstateSaleModel model = realEstateSaleModels.get(position);
            holder.tvTitle.setText(model.getPropertyNames());
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
        if (realEstateSaleModels != null) {
            return realEstateSaleModels.size();
        } else {
            return 0;
        }
    }

    public void setRealEstatesSale(List<RealEstateSaleModel> realEstateSale) {
        this.realEstateSaleModels = realEstateSale;
        notifyDataSetChanged();
    }

    public class SaleViewHolder extends RecyclerView.ViewHolder {

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

        public SaleViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(realEstateSaleModels.get(position));
                }
            });

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(RealEstateSaleModel realEstateSaleModel);
    }
}
