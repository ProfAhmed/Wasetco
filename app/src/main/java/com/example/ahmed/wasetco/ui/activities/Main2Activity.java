package com.example.ahmed.wasetco.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.wasetco.R;
import com.example.ahmed.wasetco.ui.fragments.AgentsFragment;
import com.example.ahmed.wasetco.ui.fragments.FeaturedFragment;
import com.example.ahmed.wasetco.ui.fragments.MapFragment;
import com.example.ahmed.wasetco.ui.fragments.RealEstateRentFragment;
import com.example.ahmed.wasetco.ui.fragments.RealEstateSaleFragment;
import com.example.ahmed.wasetco.ui.fragments.RealEstatesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @BindView(R.id.tvRentTab)
    TextView tvRentTab;
    @BindView(R.id.tvSaleTab)
    TextView tvSaleTab;
    @BindView(R.id.btnCity)
    Button btnCity;
    @BindView(R.id.btnGovernment)
    Button btnGovernment;
    @BindView(R.id.tabs)
    LinearLayout tabs;
    @BindView(R.id.cityAndGovernment)
    LinearLayout cityAndGovernmentLinear;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.real_estates));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();

        MapFragment dataFragment = (MapFragment) fm.findFragmentByTag("map");// to prevent fragment
        tvRentTab.setOnClickListener(this::onClick);
        tvSaleTab.setOnClickListener(this::onClick);
        btnGovernment.setOnClickListener(this::onClick);
        btnCity.setOnClickListener(this::onClick);


        // create the fragment and data the first time
        if (dataFragment == null) {
            fragmentShow(MapFragment.class, "map");
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        String[] governments = {"Cairo", "Alex", "Gisa", "Cairo", "Alex", "Gisa", "Cairo", "Alex", "Gisa", "Cairo", "Alex", "Gisa"
                , "Cairo", "Alex", "Gisa", "Cairo", "Alex", "Gisa", "Cairo", "Alex", "Gisa", "Cairo", "Alex", "Gisa", "Cairo", "Alex", "Gisa"};
        String[] cities = {"Haram", "October", "Nasr City"};
        int id = v.getId();
        if (id == R.id.btnGovernment) {
            for (int i = 0; i < governments.length; i++) {
                menu.add(Menu.NONE, i, Menu.NONE, governments[i]);
            }
        } else {

            for (int i = 0; i < cities.length; i++) {
                menu.add(Menu.NONE, i, Menu.NONE, cities[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_map) {
            fragmentShow(MapFragment.class, "map");
            tabs.setVisibility(View.VISIBLE);
            toolbar.setTitle(R.string.action_map);
            return true;
        } else if (id == R.id.action_search) {
            Intent intent = new Intent(this, FilterActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.menu_home) {
            tabs.setVisibility(View.GONE);
            toolbar.setTitle(getResources().getString(R.string.real_estates));
            fragmentShow(RealEstatesFragment.class, "home");

        } else if (id == R.id.menu_featured) {
            tabs.setVisibility(View.GONE);
            toolbar.setTitle(getResources().getString(R.string.featured));
            fragmentShow(FeaturedFragment.class, "home");

        } else if (id == R.id.menu_forSale) {
            tabs.setVisibility(View.GONE);
            toolbar.setTitle(getResources().getString(R.string.sale));
            fragmentShow(RealEstateSaleFragment.class, "sale");

        } else if (id == R.id.menu_ForRent) {
            tabs.setVisibility(View.GONE);

            toolbar.setTitle(getResources().getString(R.string.rent));
            fragmentShow(RealEstateRentFragment.class, "rent");
        } else if (id == R.id.menu_agents) {
            tabs.setVisibility(View.GONE);
            cityAndGovernmentLinear.setVisibility(View.GONE);
            toolbar.setTitle(getResources().getString(R.string.agents));
            fragmentShow(AgentsFragment.class, "agents");

        } else if (id == R.id.menu_sayUs) {
            tabs.setVisibility(View.GONE);

        } else if (id == R.id.menu_about) {
            tabs.setVisibility(View.GONE);


        } else if (id == R.id.menu_contactUs) {
            tabs.setVisibility(View.GONE);

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
                .replace(R.id.main2Container, fragment, tag).commit();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.tvRentTab:
                fragmentShow(RealEstateRentFragment.class, "rent");
                tvRentTab.setTextColor(Color.WHITE);
                tvSaleTab.setTextColor(Color.parseColor("#dad9d9"));
                toolbar.setTitle(R.string.rent);
                break;

            case R.id.tvSaleTab:
                fragmentShow(RealEstateSaleFragment.class, "sale");
                tvSaleTab.setTextColor(Color.WHITE);
                tvRentTab.setTextColor(Color.parseColor("#dad9d9"));
                toolbar.setTitle(R.string.sale);
                break;
            case R.id.btnGovernment:
                registerForContextMenu(btnGovernment);
                openContextMenu(btnGovernment);
                break;

            case R.id.btnCity:
                registerForContextMenu(btnCity);
                openContextMenu(btnCity);
                break;

            default:
        }
    }
}
