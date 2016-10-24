package com.bsunk.theredplanetmars.roverimages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.bsunk.theredplanetmars.R;

public class RoverImagesActivity extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    int selected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_images);

        navigationView = (NavigationView) findViewById(R.id.nvView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

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

        //if restoring activity then restore fragment and title.
        if(savedInstanceState!=null) {
            selected = savedInstanceState.getInt("roverid");
            FragmentManager fm = getSupportFragmentManager();
            if (fm.findFragmentByTag(Integer.toString(selected))!= null) {
                fm.beginTransaction()
                        .replace(R.id.rover_images_container, getSupportFragmentManager().findFragmentByTag(Integer.toString(selected)), Integer.toString(selected))
                        .commit();
                fm.executePendingTransactions();
            }
        }
        else {
            launchSelectedFragment(selected);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("roverid", selected);
        super.onSaveInstanceState(bundle);
    }

    //Takes care of the selection in NavigationView
    public boolean navigationSelected(MenuItem item) {
        drawerLayout.closeDrawers();
        item.setChecked(true);
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

        if(selected==0) {
            navigationView.setCheckedItem(R.id.nav_curiosity);
            FragmentManager fm = RoverImagesActivity.this.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.rover_images_container, RoverImagesFragment.newInstance(RoverImagesPresenter.CURIOSITY), Integer.toString(selected));
            ft.commit();
            fm.executePendingTransactions();
        }
        else if(selected==1) {
            FragmentManager fm = RoverImagesActivity.this.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.rover_images_container, RoverImagesFragment.newInstance(RoverImagesPresenter.OPPORTUNITY), Integer.toString(selected));
            ft.commit();
            fm.executePendingTransactions();
        }
        else if(selected==2) {
            FragmentManager fm = RoverImagesActivity.this.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.rover_images_container, RoverImagesFragment.newInstance(RoverImagesPresenter.SPIRIT), Integer.toString(selected));
            ft.commit();
            fm.executePendingTransactions();
        }
    }

}
