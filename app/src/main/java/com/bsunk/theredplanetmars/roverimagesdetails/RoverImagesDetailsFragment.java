package com.bsunk.theredplanetmars.roverimagesdetails;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.model.Photo;
import com.bsunk.theredplanetmars.roverimages.RoverImagesFragment;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class RoverImagesDetailsFragment extends Fragment {

    ImageView imageView;
    TextView cameraTextView;
    TextView dateTextView;
    TextView martianSolTextView;
    TextView roverName;

    public RoverImagesDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rover_images_details, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.rover_image);
        cameraTextView = (TextView) rootView.findViewById(R.id.detail_camera);
        dateTextView = (TextView) rootView.findViewById(R.id.detail_date);
        martianSolTextView = (TextView) rootView.findViewById(R.id.detail_martian_sol);
        roverName = (TextView) rootView.findViewById(R.id.detail_rover_name) ;
        Bundle arguments = getArguments();
        Gson gson = new Gson();
        String obj = arguments.getString(RoverImagesFragment.PHOTO_KEY);
        Photo photo = gson.fromJson(obj, Photo.class);

        Picasso.with(getContext()).load(photo.getImgSrc()).into(imageView);

        cameraTextView.setText(photo.getCamera().getFullName());
        dateTextView.setText(photo.getEarthDate());
        martianSolTextView.setText(photo.getSol().toString());
        roverName.setText(photo.getRover().getName());

        return rootView;
    }


}
