package com.bsunk.theredplanetmars.roverimagesdetails;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.model.Photo;
import com.bsunk.theredplanetmars.roverimages.RoverImagesFragment;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A placeholder fragment containing a simple view.
 */
public class RoverImagesDetailsFragment extends Fragment implements RoverImagesDetailsContract.View {

    TextView cameraTextView;
    TextView dateTextView;
    TextView martianSolTextView;
    TextView roverName;
    PhotoView imageView;

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
        if(arguments!=null) {
            Gson gson = new Gson();
            String obj = arguments.getString(RoverImagesFragment.PHOTO_KEY);
            Photo photo = gson.fromJson(obj, Photo.class);
            mActionsListener.openDetails(photo);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rover_images_details, container, false);

        imageView = (PhotoView) rootView.findViewById(R.id.rover_image);
        cameraTextView = (TextView) rootView.findViewById(R.id.detail_camera);
        dateTextView = (TextView) rootView.findViewById(R.id.detail_date);
        martianSolTextView = (TextView) rootView.findViewById(R.id.detail_martian_sol);
        roverName = (TextView) rootView.findViewById(R.id.detail_rover_name) ;
        RelativeLayout infoPanel = (RelativeLayout) rootView.findViewById(R.id.details_info_panel);
        ImageButton backButton = (ImageButton) rootView.findViewById(R.id.back_button);

        Animation slideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.up_from_bottom );
        infoPanel.startAnimation(slideUp);

        Animation slideDown = AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top);
        backButton.startAnimation(slideDown);

        return rootView;
    }

    @Override
    public void showImage(String imageURL) {
        final PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);

        Picasso.with(getContext()).invalidate(imageURL);
        Picasso.with(getContext()).load(imageURL)
                .into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                attacher.update();
            }

            @Override
            public void onError() {
            }
        });
    }

    @Override
    public void showInfo(Photo photo) {
        cameraTextView.setText(photo.getCamera().getFullName());
        dateTextView.setText(photo.getEarthDate());
        martianSolTextView.setText(photo.getSol().toString());
        roverName.setText(photo.getRover().getName());
    }
    
}
