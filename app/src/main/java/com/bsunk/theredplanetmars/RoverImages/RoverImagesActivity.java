package com.bsunk.theredplanetmars.roverimages;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewAnimationUtils;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.roverfavorites.RoverFavoritesFragment;

import io.realm.Realm;

public class RoverImagesActivity extends AppCompatActivity {

    int selected = 0;
    AHBottomNavigation bottomNavigation;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_images);
        Realm.init(getApplicationContext());
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.curiosity, R.drawable.curiosity, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.opportunity, R.drawable.opportunity, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.spirit, R.drawable.spirit, R.color.colorAccent);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.favorites, R.drawable.ic_favorite_black_24dp, R.color.colorAccent);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimaryDark));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.colorAccent));

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                selected = position;
                launchSelectedFragment(selected, false);
                return true;
            }
        });

        //if restoring activity then restore fragment and title.
        if(savedInstanceState!=null) {
            selected = savedInstanceState.getInt("roverid");
            launchSelectedFragment(selected, true);
        }
        else {
            selected=0;
            launchSelectedFragment(selected, true);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("roverid", selected);
        super.onSaveInstanceState(bundle);
    }

    private void launchSelectedFragment(int selected, boolean isConfigChange) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (fm.findFragmentByTag(Integer.toString(selected))!= null) {
            fm.beginTransaction()
                    .replace(R.id.rover_images_container, getSupportFragmentManager().findFragmentByTag(Integer.toString(selected)), Integer.toString(selected))
                    .commit();
        }
        else {
            int startRadius = 0;
            int endRadius = (int) Math.hypot(coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
            Animator anim;
            int x,y;

            if (selected == 0) {
                if(!isConfigChange) {
                    x = bottomNavigation.getLeft();
                    y = bottomNavigation.getBottom();
                    anim = ViewAnimationUtils.createCircularReveal(coordinatorLayout, x, y, startRadius, endRadius);
                    anim.start();
                }
                ft.replace(R.id.rover_images_container, RoverImagesFragment.newInstance(RoverImagesPresenter.CURIOSITY), Integer.toString(selected));
                ft.commit();
            } else if (selected == 1) {
                if(!isConfigChange) {
                    x = (bottomNavigation.getRight() - bottomNavigation.getLeft()) / 2;
                    y = bottomNavigation.getBottom();
                    startRadius = 0;
                    endRadius = (int) Math.hypot(coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                    anim = ViewAnimationUtils.createCircularReveal(coordinatorLayout, x, y, startRadius, endRadius);
                    anim.start();
                }
                ft.replace(R.id.rover_images_container, RoverImagesFragment.newInstance(RoverImagesPresenter.OPPORTUNITY), Integer.toString(selected));
                ft.commit();
            } else if (selected == 2) {
                if(!isConfigChange) {
                    x = (bottomNavigation.getRight() - bottomNavigation.getLeft()) / 2;
                    y = bottomNavigation.getBottom();
                    startRadius = 0;
                    endRadius = (int) Math.hypot(coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                    anim = ViewAnimationUtils.createCircularReveal(coordinatorLayout, x, y, startRadius, endRadius);
                    anim.start();
                }
                ft.replace(R.id.rover_images_container, RoverImagesFragment.newInstance(RoverImagesPresenter.SPIRIT), Integer.toString(selected));
                ft.commit();
            }
            else if (selected == 3) {
                if(!isConfigChange) {
                    x = bottomNavigation.getRight();
                    y = bottomNavigation.getBottom();
                    startRadius = 0;
                    endRadius = (int) Math.hypot(coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                    anim = ViewAnimationUtils.createCircularReveal(coordinatorLayout, x, y, startRadius, endRadius);
                    anim.start();
                }
                ft.replace(R.id.rover_images_container, new RoverFavoritesFragment(), Integer.toString(selected));
                ft.commit();
            }
        }
    }

}