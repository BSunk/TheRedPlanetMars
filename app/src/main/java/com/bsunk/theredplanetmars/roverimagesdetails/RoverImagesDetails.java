package com.bsunk.theredplanetmars.roverimagesdetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.model.Photo;
import com.bsunk.theredplanetmars.roverimages.RoverImagesFragment;

import org.parceler.Parcels;

public class RoverImagesDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_images_details);
        Photo photo = Parcels.unwrap(getIntent().getParcelableExtra(RoverImagesFragment.PHOTO_KEY));
        RoverImagesDetailsFragment fragment = new RoverImagesDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(RoverImagesFragment.PHOTO_KEY, Parcels.wrap(photo));
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.details_container, fragment)
                .commit();
    }

    public void backButtonOnClick(View view) {
        onBackPressed();
    }

}
