package com.example.ahmed.wasetco.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ahmed.wasetco.R;
import com.example.ahmed.wasetco.data.Constants;
import com.example.ahmed.wasetco.data.models.RealEstateModel;
import com.example.ahmed.wasetco.viewmodels.RealEstateViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapFragment extends Fragment implements ClusterManager.OnClusterClickListener<RealEstateModel>,
        ClusterManager.OnClusterInfoWindowClickListener<RealEstateModel>,
        ClusterManager.OnClusterItemClickListener<RealEstateModel>,
        ClusterManager.OnClusterItemInfoWindowClickListener<RealEstateModel>, OnMapReadyCallback {

    @BindView(R.id.mapView)
    MapView mMapView;
    private GoogleMap mMap;

    private ClusterManager<RealEstateModel> mClusterManager;
    private Cluster<RealEstateModel> clickedCluster;
    private RealEstateModel clickedClusterItem;

    RealEstateViewModel viewModel;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewModel = ViewModelProviders.of(this).get(RealEstateViewModel.class);

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        // Creating cluster manager object.


        mClusterManager = new ClusterManager<RealEstateModel>(getActivity(), mMap);
        mMap.setOnCameraIdleListener(mClusterManager);
        mClusterManager.setRenderer(new CustomClusterRenderer(getActivity(), mMap, mClusterManager));
        mMap.setOnInfoWindowClickListener(mClusterManager);
        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());
        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(new MyCustomAdapterForItems());
        mMap.setOnMarkerClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        viewModel.getRealEstates(1).observe(getActivity(), realEstateModels -> {

            // Adding Objects to the Cluster.

            for (int i = 0; i < realEstateModels.size(); i++) {

                mClusterManager.addItem(realEstateModels.get(i));
            }

            mClusterManager.cluster();
        });

    }

    @Override
    public void onClusterItemInfoWindowClick(RealEstateModel item) {
        Toast.makeText(getActivity(), "info window" + item.getPropertyTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onClusterItemClick(RealEstateModel item) {
        // TODO Auto-generated method stub
        clickedClusterItem = item;
        Toast.makeText(getActivity(), item.getPropertyTitle(), Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<RealEstateModel> cluster) {
        // TODO Auto-generated method stub
        Toast.makeText(getActivity(), "ClusterInfoWindowClick" + cluster.getItems().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onClusterClick(Cluster<RealEstateModel> cluster) {
        // TODO Auto-generated method stub
        clickedCluster = cluster;
        Toast.makeText(getActivity(), "Cluster" + cluster.getPosition().toString(), Toast.LENGTH_SHORT).show();


        // Zoom in the cluster. Need to create LatLngBounds and including all the cluster items
        // inside of bounds, then animate to center of the bounds.

        LatLng latLng = new LatLng(29.5154615, 29.22222);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 6);
        mMap.animateCamera(cameraUpdate);
        // Create the builder to collect all essential cluster items for the bounds.

        return false;
    }


    // Custom adapter info view :
    public class MyCustomAdapterForItems implements GoogleMap.InfoWindowAdapter {

        @BindView(R.id.ivInfoWindowRealEstate)
        ImageView ivRealEstate;

        @BindView(R.id.tvTitleInfo)
        TextView tvTitle;

        @BindView(R.id.tvAddressInfo)
        TextView tvAddress;

        @BindView(R.id.tvBedroomInfo)
        TextView tvBedroom;

        @BindView(R.id.tvBathroomInfo)
        TextView tvBathroom;

        @BindView(R.id.btnMoreInfo)
        Button btnMoreInfo;

        @BindView(R.id.iBtnCloseWindow)
        ImageButton iBtnClose;

        private final View myContentsView;


        MyCustomAdapterForItems() {
            myContentsView = getLayoutInflater().inflate(
                    R.layout.realestate_info_window, null);


            ButterKnife.bind(this, myContentsView);
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

        @Override
        public View getInfoWindow(Marker marker) {

            if (clickedClusterItem != null) {

                tvTitle.setText(clickedClusterItem.getPropertyTitle());
                tvAddress.setText(clickedClusterItem.getAddress());
                tvBathroom.setText(clickedClusterItem.getBathrooms());
                tvBedroom.setText(clickedClusterItem.getBedrooms());
                new DownloadMarkerTask(getActivity(), ivRealEstate).execute();

                btnMoreInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "btnMoreInfo", Toast.LENGTH_SHORT).show();
                    }
                });

                iBtnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        marker.hideInfoWindow();
                        Toast.makeText(getActivity(), "btnMoreInfo", Toast.LENGTH_SHORT).show();
                    }
                });

            }
            return myContentsView;
        }
    }

    public class CustomClusterRenderer extends DefaultClusterRenderer<RealEstateModel> {

        private final IconGenerator mClusterIconGenerator = new IconGenerator(getActivity());

        public CustomClusterRenderer(Context context, GoogleMap map,
                                     ClusterManager<RealEstateModel> clusterManager) {
            super(context, map, clusterManager);

        }

        @Override
        protected void onBeforeClusterItemRendered(RealEstateModel item,
                                                   MarkerOptions markerOptions) {
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<RealEstateModel> cluster, MarkerOptions markerOptions) {
            super.onBeforeClusterRendered(cluster, markerOptions);

        }
    }


    private class DownloadMarkerTask extends AsyncTask<Bitmap, Void, Bitmap> {

        Context mContext;
        ImageView ivRealEstate;

        DownloadMarkerTask(Context mContext, ImageView ivRealEstate) {
            this.mContext = mContext;
            this.ivRealEstate = ivRealEstate;
        }

        @Override
        protected Bitmap doInBackground(Bitmap... imageBitmaps) {
            Bitmap image = null;
            try {
                image = Glide.with(getActivity()).asBitmap()
                        .load(Constants.IMAGE_URL + clickedClusterItem.getFeaturedImage() + "-b.jpg")
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .submit(250, 250)
                        .get();


            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            super.onPostExecute(image);
            ivRealEstate.setImageBitmap(image);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
