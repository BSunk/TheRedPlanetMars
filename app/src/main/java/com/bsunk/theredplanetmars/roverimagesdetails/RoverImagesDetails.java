package com.bsunk.theredplanetmars.roverimagesdetails;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.model.Photo;
import com.bsunk.theredplanetmars.roverimages.RoverImagesFragment;
import com.google.gson.Gson;

public class RoverImagesDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_images_details);

        String obj = getIntent().getStringExtra(RoverImagesFragment.PHOTO_KEY);

        if(savedInstanceState==null) {
            Bundle arguments = new Bundle();
            arguments.putString(RoverImagesFragment.PHOTO_KEY, obj);
            RoverImagesDetailsFragment fragment = new RoverImagesDetailsFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.details_container, fragment)
                    .commit();
        }
    }

    public void backButtonOnClick(View view) {
        onBackPressed();
    }

}
