package com.example.ahmed.wasetco.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ahmed.wasetco.R;
import com.example.ahmed.wasetco.ui.activities.FilterActivity;
import com.example.ahmed.wasetco.ui.activities.MainActivity;
import com.example.ahmed.wasetco.ui.adapters.RealEstatesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.btnHome)
    ImageButton btnHome;
    @BindView(R.id.btnMap)
    ImageButton btnMap;
    @BindView(R.id.btnFilter)
    ImageButton btnFilter;
    @BindView(R.id.btnStar)
    ImageButton btnStar;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentShow(RealEstatesFragment.class, "realEsatet");
        Toast.makeText(getActivity(), "Home", Toast.LENGTH_SHORT).show();
        btnHome.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        btnStar.setOnClickListener(this);
    }

    private void fragmentShow(Class fragmentClass, String tag) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.homeContainer, fragment, tag).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHome:
                fragmentShow(RealEstatesFragment.class, "realEsatet");
                MainActivity.tvPageTitle.setText("Real Estates");
                break;
            case R.id.btnMap:
                fragmentShow(MapFragment.class, "map");
                MainActivity.tvPageTitle.setText("Map");
                break;
            case R.id.btnFilter:
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
                break;
            case R.id.btnStar:
                fragmentShow(StarFragment.class, "start");
                MainActivity.tvPageTitle.setText("Featured Real Estates");

        }

    }
}
