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
    int selected = 0;

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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return navigationSelected(item);
            }
        });

        if(savedInstanceState!=null) {
            selected = savedInstanceState.getInt("roverid");
        }
        launchSelectedFragment(selected);

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("roverid", selected);
    }

    //Takes care of the selection in NavigationView
    public boolean navigationSelected(MenuItem item) {
        drawerLayout.closeDrawers();
        switch(item.getItemId()) {
            case R.id.nav_curiosity:
                selected=0;
                launchSelectedFragment(selected);
                return true;
            case R.id.nav_opportunity:
                selected=1;
                launchSelectedFragment(selected);
                return true;
            case R.id.nav_spirit:
                selected=2;
                launchSelectedFragment(selected);
                return true;
        }
        return false;
    }

    private void launchSelectedFragment(int selected) {
        RoverImagesFragment fragment = new RoverImagesFragment();
        Bundle bundle = new Bundle();

        if(selected==0) {
            toolbarTitle.setText(R.string.curiosity);
            bundle.putInt(RoverImagesPresenter.ROVER_KEY, RoverImagesPresenter.CURIOSITY);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rover_images_container, fragment, "curiosity")
                    .commit();
        }
        else if(selected==1) {
            toolbarTitle.setText(R.string.opportunity);
            bundle.putInt(RoverImagesPresenter.ROVER_KEY, RoverImagesPresenter.OPPORTUNITY);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rover_images_container, fragment, "opportunity")
                    .commit();
        }
        else if(selected==2) {
            toolbarTitle.setText(R.string.spirit);
            bundle.putInt(RoverImagesPresenter.ROVER_KEY, RoverImagesPresenter.SPIRIT);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rover_images_container, fragment, "spirit")
                    .commit();
        }
    }

}
