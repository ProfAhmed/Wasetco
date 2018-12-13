package com.example.ahmed.wasetco.ui.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ahmed.wasetco.R;
import com.example.ahmed.wasetco.ui.fragments.AgentsFragment;
import com.example.ahmed.wasetco.ui.fragments.HomeFragment;
import com.example.ahmed.wasetco.ui.fragments.RealEstateRentFragment;
import com.example.ahmed.wasetco.ui.fragments.RealEstateSaleFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static TextView tvPageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        tvPageTitle = (TextView) findViewById(R.id.tvPageTitle);
        ImageButton b = (ImageButton) findViewById(R.id.btnOpenMenu);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Locale.getDefault().getISO3Language().endsWith("ara")) {
                    drawer.openDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.LEFT);
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();

        HomeFragment dataFragment = (HomeFragment) fm.findFragmentByTag("league");// to prevent fragment

        // create the fragment and data the first time
        if (dataFragment == null) {
            fragmentShow(HomeFragment.class, "league");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_home) {

            tvPageTitle.setText("Real Estates");
            fragmentShow(HomeFragment.class, "league");
        } else if (id == R.id.menu_filter) {

        } else if (id == R.id.menu_forSale) {

            tvPageTitle.setText("Buy");
            fragmentShow(RealEstateSaleFragment.class, "buy");

        } else if (id == R.id.menu_ForRent) {

            tvPageTitle.setText("Rent");
            fragmentShow(RealEstateRentFragment.class, "rent");
        } else if (id == R.id.menu_agents) {

            tvPageTitle.setText("Agents");
            fragmentShow(AgentsFragment.class, "agents");
        } else if (id == R.id.menu_sayUs) {

        } else if (id == R.id.menu_about) {

        } else if (id == R.id.menu_contactUs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void fragmentShow(Class fragmentClass, String tag) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, fragment, tag).commit();
    }
}
