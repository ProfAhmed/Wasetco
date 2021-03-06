package com.example.ahmed.wasetco.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

public class MapsActivity extends AppCompatActivity implements ClusterManager.OnClusterClickListener<RealEstateModel>,
        ClusterManager.OnClusterInfoWindowClickListener<RealEstateModel>,
        ClusterManager.OnClusterItemClickListener<RealEstateModel>,
        ClusterManager.OnClusterItemInfoWindowClickListener<RealEstateModel>, OnMapReadyCallback {

    private GoogleMap mMap;
    private Toolbar toolbar;

    private ClusterManager<RealEstateModel> mClusterManager;
    private Cluster<RealEstateModel> clickedCluster;
    private RealEstateModel clickedClusterItem;

    RealEstateViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        viewModel = ViewModelProviders.of(this).get(RealEstateViewModel.class);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Creating cluster manager object.

        mClusterManager = new ClusterManager<RealEstateModel>(this, mMap);
        mMap.setOnCameraIdleListener(mClusterManager);
        mClusterManager.setRenderer(new CustomClusterRenderer(this, mMap, mClusterManager));
        mMap.setOnInfoWindowClickListener(mClusterManager);
        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());
        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(new MyCustomAdapterForItems());
        mMap.setOnMarkerClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        viewModel.getRealEstates(1).observe(this, realEstateModels -> {

            // Adding Objects to the Cluster.

            for (int i = 0; i < realEstateModels.size(); i++) {

                mClusterManager.addItem(realEstateModels.get(i));
            }

            mClusterManager.cluster();
        });
    }

    @Override
    public void onClusterItemInfoWindowClick(RealEstateModel item) {
        Toast.makeText(this, "info window" + item.getPropertyTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onClusterItemClick(RealEstateModel item) {
        // TODO Auto-generated method stub
        clickedClusterItem = item;
        Toast.makeText(this, item.getPropertyTitle(), Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<RealEstateModel> cluster) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "ClusterInfoWindowClick" + cluster.getItems().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onClusterClick(Cluster<RealEstateModel> cluster) {
        // TODO Auto-generated method stub
        clickedCluster = cluster;
        Toast.makeText(this, "Cluster" + cluster.getPosition().toString(), Toast.LENGTH_SHORT).show();

        LatLng latLng = new LatLng(30.222, 29.25485);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 4);
        mMap.animateCamera(cameraUpdate);

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
                new DownloadMarkerTask(MapsActivity.this, ivRealEstate).execute();

                btnMoreInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MapsActivity.this, "btnMoreInfo", Toast.LENGTH_SHORT).show();
                    }
                });

                iBtnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        marker.hideInfoWindow();
                        Toast.makeText(MapsActivity.this, "btnMoreInfo", Toast.LENGTH_SHORT).show();
                    }
                });

            }
            return myContentsView;
        }
    }

    public class CustomClusterRenderer extends DefaultClusterRenderer<RealEstateModel> {

        private final IconGenerator mClusterIconGenerator = new IconGenerator(MapsActivity.this);

        public CustomClusterRenderer(Context context, GoogleMap map,
                                     ClusterManager<RealEstateModel> clusterManager) {
            super(context, map, clusterManager);

        }

        @Override
        protected void onBeforeClusterItemRendered(RealEstateModel item,
                                                   MarkerOptions markerOptions) {


            Drawable drawable = getResources().getDrawable(R.drawable.ic_home_black_24dp);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(drawableToBitmap(drawable)));


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
                image = Glide.with(MapsActivity.this).asBitmap()
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


    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
