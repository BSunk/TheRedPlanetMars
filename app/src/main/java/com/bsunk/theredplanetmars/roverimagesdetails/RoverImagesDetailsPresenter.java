package com.bsunk.theredplanetmars.roverimagesdetails;

import android.content.pm.PackageManager;
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

    @Override
    public void shareButtonClick() {
        mDetailsView.requestPermissions();
    }

    @Override
    public void onPermissionResultReturned(int code, int[] grantResults) {
        if (code==RoverImagesDetailsFragment.MY_PERMISSIONS_REQUEST_WRITE_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mDetailsView.onShareItem();
            } else {
                mDetailsView.showShareErrorToast();
            }
        }
    }

    @Override
    public void noPermissionsNeeded() {
        mDetailsView.onShareItem();
    }

}
