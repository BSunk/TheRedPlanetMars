package com.bsunk.theredplanetmars.roverimagesdetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.roverimages.RoverImagesFragment;

public class RoverImagesDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_images_details);
        String obj = getIntent().getStringExtra(RoverImagesFragment.PHOTO_KEY);
        initFragment(RoverImagesDetailsFragment.newInstance(obj));
    }

    private void initFragment(Fragment detailFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.details_container, detailFragment);
        transaction.commit();
    }

    public void backButtonOnClick(View view) {
        onBackPressed();
    }

}
