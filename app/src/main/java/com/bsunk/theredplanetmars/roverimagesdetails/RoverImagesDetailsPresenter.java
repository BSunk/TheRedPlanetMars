package com.bsunk.theredplanetmars.roverimagesdetails;

import android.support.annotation.NonNull;

import com.bsunk.theredplanetmars.model.Photo;

/**
 * Created by Bharat on 10/13/2016.
 */

public class RoverImagesDetailsPresenter implements RoverImagesDetailsContract.UserActionsListener {

    private final RoverImagesDetailsContract.View mDetailsView;

    public RoverImagesDetailsPresenter(@NonNull RoverImagesDetailsContract.View detailsView) {

        mDetailsView = detailsView;
    }

    @Override
    public void openDetails(Photo photo) {
        mDetailsView.showImage(photo.getImgSrc());
        mDetailsView.showInfo(photo);
    }

}
