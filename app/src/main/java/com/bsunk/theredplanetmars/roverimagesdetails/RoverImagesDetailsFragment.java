package com.bsunk.theredplanetmars.roverimagesdetails;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.model.Photo;
import com.bsunk.theredplanetmars.roverimages.RoverImagesFragment;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

/**
 * A placeholder fragment containing a simple view.
 */
public class RoverImagesDetailsFragment extends Fragment {

    ImageView imageView;

    public RoverImagesDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rover_images_details, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.rover_image);

        Bundle arguments = getArguments();
        Gson gson = new Gson();
        String obj = arguments.getString(RoverImagesFragment.PHOTO_KEY);
        Photo photo = gson.fromJson(obj, Photo.class);

        Glide.with(getContext()).load(photo.getImgSrc()).into(imageView);

        return rootView;

    }
}
