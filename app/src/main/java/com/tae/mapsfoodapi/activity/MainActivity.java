package com.tae.mapsfoodapi.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tae.mapsfoodapi.R;
import com.tae.mapsfoodapi.constants.Constants;
import com.tae.mapsfoodapi.fragment.FragmentFood;
import com.tae.mapsfoodapi.listener.OnPlacesLoadedListener;
import com.tae.mapsfoodapi.model.local.PlacesModel;
import com.tae.mapsfoodapi.service.FoodService;

import butterknife.Bind;
import butterknife.ButterKnife;
import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, OnPlacesLoadedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;
    @Bind(R.id.navigation_view)
    protected NavigationView mNavigationView;
    private FoodReceiver foodReceiver;
    private PlacesModel placesModel;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        initDrawerToggle();
        initFoodReceiver();
        checkForUpdates();

//        progressDialog = new ProgressDialog(this);
    }

    /**
     *Hockey Integration
     */
    private void checkForCrashes() {
        CrashManager.register(this, Constants.HOCKEY_APP_ID);
    }

    /**
     *Hockey Integration
     */
    private void checkForUpdates() {
        // Remove this for store / production builds!
        UpdateManager.register(this, Constants.HOCKEY_APP_ID);
    }


    private void handleLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Constants.PERMISSIONS_REQUEST_FINE_LOCATION);
            } else {
                startService(FoodService.makeIntent(this));
            }
        } else {
            startService(FoodService.makeIntent(this));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.PERMISSIONS_REQUEST_FINE_LOCATION :
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startService(FoodService.makeIntent(this));
                    Toast.makeText(this, "Location granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void initFoodReceiver() {
        foodReceiver = new FoodReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForCrashes(); /*Hockey*/
        registerFoodReceiver();
    }

    private void registerFoodReceiver() {
        unregisterFoodReceiver();
    }

    private void unregisterFoodReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(foodReceiver, new IntentFilter(Constants.ACTION_DOWNLOAD_SUCCESS));
    }

    @Override
    protected void onPause() {
        super.onPause();
        UpdateManager.unregister(); /*Hockey*/
        LocalBroadcastManager.getInstance(this).unregisterReceiver(foodReceiver);
    }

    private void initDrawerToggle() {
        mNavigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        toggle.syncState();
        mDrawerLayout.setDrawerListener(toggle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.food) {
            handleLocationPermission();
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void arePlacesLoaded(boolean areLoaded) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public class FoodReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO handle data
            if (intent.getParcelableExtra(Constants.EXTRA_PLACES_MODEL) != null) {
                placesModel = intent.getParcelableExtra(Constants.EXTRA_PLACES_MODEL);
                Log.i(TAG, "onReceive: places 1: " + placesModel.getPlaces().get(0).getName());
                // TODO display fragment
                displayFragmentFood();
            } else {
                progressDialog.dismiss();
            }
        }
    }

    private void displayFragmentFood() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container, FragmentFood.newInstance(placesModel), FragmentFood.TAG).commit();
    }

}
