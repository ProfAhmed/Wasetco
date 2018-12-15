package com.example.ahmed.wasetco.ui.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ahmed.wasetco.R;
import com.example.ahmed.wasetco.data.models.RealEstateModel;
import com.example.ahmed.wasetco.viewmodels.RealEstateViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    @BindView(R.id.mapView)
    MapView mMapView;
    private GoogleMap mMap;

    RealEstateViewModel viewModel;

    private ClusterManager<RealEstateModel> mClusterManager;


    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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


        viewModel = ViewModelProviders.of(getActivity()).get(RealEstateViewModel.class);

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

        mClusterManager = new ClusterManager<RealEstateModel>(getActivity(), mMap);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        viewModel.getRealEstates().observe(getActivity(), new Observer<ArrayList<RealEstateModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<RealEstateModel> realEstateModels) {

                for (int i = 0; i < realEstateModels.size(); i++) {


                    mClusterManager.addItem(realEstateModels.get(i));
                }

                mClusterManager.cluster();

                final CustomClusterRenderer renderer = new CustomClusterRenderer(getActivity(), mMap, mClusterManager);

                mClusterManager.setRenderer(renderer);

                mClusterManager
                        .setOnClusterClickListener(new ClusterManager.OnClusterClickListener<RealEstateModel>() {
                            @Override
                            public boolean onClusterClick(final Cluster<RealEstateModel> cluster) {
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                        cluster.getPosition(), (float) Math.floor(mMap
                                                .getCameraPosition().zoom + 1)), 300,
                                        null);
                                return true;
                            }
                        });

                mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<RealEstateModel>() {
                    @Override
                    public boolean onClusterItemClick(RealEstateModel realEstateModel) {

                        Toast.makeText(getActivity(), realEstateModel.getPropertyTitle(), Toast.LENGTH_SHORT).show();

                        
                        return true;
                    }
                });

                Toast.makeText(getActivity(), realEstateModels.get(0).getPropertyTitle(), Toast.LENGTH_SHORT).show();
            }
        });


    }



    public class CustomClusterRenderer extends DefaultClusterRenderer<RealEstateModel> {

        private final Context mContext;

        public CustomClusterRenderer(Context context, GoogleMap map,
                                     ClusterManager<RealEstateModel> clusterManager) {
            super(context, map, clusterManager);

            mContext = context;
        }

        @Override
        protected void onBeforeClusterItemRendered(RealEstateModel item,
                                                   MarkerOptions markerOptions) {


            markerOptions.icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_home_black_24dp)).snippet(item.getPropertyTitle());

        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_home_black_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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
