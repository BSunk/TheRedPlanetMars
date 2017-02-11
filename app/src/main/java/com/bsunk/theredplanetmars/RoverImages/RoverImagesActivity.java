package com.bsunk.theredplanetmars.roverimages;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bsunk.theredplanetmars.R;

public class RoverImagesActivity extends AppCompatActivity {

    int selected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_images);
        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.curiosity, R.drawable.curiosity, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.opportunity, R.drawable.opportunity, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.spirit, R.drawable.spirit, R.color.colorAccent);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimaryDark));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.colorAccent));

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if(position==0) { selected=0;}
                else if (position==1) {selected=1;}
                else if (position==2) {selected=2;}

                launchSelectedFragment(selected);
                return true;
            }
        });


        //if restoring activity then restore fragment and title.
        if(savedInstanceState!=null) {
            selected = savedInstanceState.getInt("roverid");
            launchSelectedFragment(selected);
        }
        else {
            selected=0;
            launchSelectedFragment(selected);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("roverid", selected);
        super.onSaveInstanceState(bundle);
    }

    private void launchSelectedFragment(int selected) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (fm.findFragmentByTag(Integer.toString(selected))!= null) {
            Log.v("LOG", String.valueOf(selected));
            fm.beginTransaction()
                    .replace(R.id.rover_images_container, getSupportFragmentManager().findFragmentByTag(Integer.toString(selected)), Integer.toString(selected))
                    .commit();
        }
        else {
            if (selected == 0) {
                ft.replace(R.id.rover_images_container, RoverImagesFragment.newInstance(RoverImagesPresenter.CURIOSITY), Integer.toString(selected));
                ft.commit();
            } else if (selected == 1) {
                ft.replace(R.id.rover_images_container, RoverImagesFragment.newInstance(RoverImagesPresenter.OPPORTUNITY), Integer.toString(selected));
                ft.commit();
            } else if (selected == 2) {
                ft.replace(R.id.rover_images_container, RoverImagesFragment.newInstance(RoverImagesPresenter.SPIRIT), Integer.toString(selected));
                ft.commit();
            }
        }
    }

}