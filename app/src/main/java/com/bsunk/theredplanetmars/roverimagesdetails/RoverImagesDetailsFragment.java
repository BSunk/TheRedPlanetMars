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
import com.bsunk.theredplanetmars.roverimages.RoverImagesContract;
import com.bsunk.theredplanetmars.roverimages.RoverImagesFragment;
import com.bsunk.theredplanetmars.roverimages.RoverImagesPresenter;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class RoverImagesDetailsFragment extends Fragment implements RoverImagesDetailsContract.View {

    ImageView imageView;
    TextView cameraTextView;
    TextView dateTextView;
    TextView martianSolTextView;
    TextView roverName;

    private RoverImagesDetailsContract.UserActionsListener mActionsListener;

    public static RoverImagesDetailsFragment newInstance(String photoObj) {
        Bundle arguments = new Bundle();
        arguments.putString(RoverImagesFragment.PHOTO_KEY, photoObj);
        RoverImagesDetailsFragment fragment = new RoverImagesDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionsListener = new RoverImagesDetailsPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle arguments = getArguments();
        Gson gson = new Gson();
        String obj = arguments.getString(RoverImagesFragment.PHOTO_KEY);
        Photo photo = gson.fromJson(obj, Photo.class);
        mActionsListener.openDetails(photo);
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
        return rootView;
    }

    @Override
    public void showImage(String imageURL) {
        Picasso.with(getContext()).load(imageURL).into(imageView);
    }

    @Override
    public void showInfo(Photo photo) {
        cameraTextView.setText(photo.getCamera().getFullName());
        dateTextView.setText(photo.getEarthDate());
        martianSolTextView.setText(photo.getSol().toString());
        roverName.setText(photo.getRover().getName());
    }


}
