package com.bsunk.theredplanetmars.roverimages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bsunk.theredplanetmars.R;

public class RoverImagesActivity extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_images);

        navigationView = (NavigationView) findViewById(R.id.nvView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mDrawerToggle.syncState();

        toolbarTitle.setText(R.string.curiosity);
        RoverImagesFragment fragment = new RoverImagesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(RoverImagesPresenter.ROVER_KEY, RoverImagesPresenter.CURIOSITY);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rover_images_container, fragment, "curiosity")
                .commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return navigationSelected(item);
            }
        });

    }

    public boolean navigationSelected(MenuItem item) {
        drawerLayout.closeDrawers();
        RoverImagesFragment fragment = new RoverImagesFragment();
        Bundle bundle = new Bundle();

        switch(item.getItemId()) {
            case R.id.nav_curiosity:
                toolbarTitle.setText(R.string.curiosity);
                bundle.putInt(RoverImagesPresenter.ROVER_KEY, RoverImagesPresenter.CURIOSITY);
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.rover_images_container, fragment, "curiosity")
                        .commit();
                return true;
            case R.id.nav_opportunity:
                toolbarTitle.setText(R.string.opportunity);
                bundle.putInt(RoverImagesPresenter.ROVER_KEY, RoverImagesPresenter.OPPORTUNITY);
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.rover_images_container, fragment, "opportunity")
                        .commit();
                return true;
            case R.id.nav_spirit:
                toolbarTitle.setText(R.string.spirit);
                bundle.putInt(RoverImagesPresenter.ROVER_KEY, RoverImagesPresenter.SPIRIT);
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.rover_images_container, fragment, "spirit")
                        .commit();
                return true;
        }
        return false;
    }

}
