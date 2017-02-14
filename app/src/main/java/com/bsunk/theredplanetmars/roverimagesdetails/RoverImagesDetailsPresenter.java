package com.bsunk.theredplanetmars.roverimagesdetails;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bsunk.theredplanetmars.data.RealmDatabase;
import com.bsunk.theredplanetmars.model.FavoritePhoto;
import com.bsunk.theredplanetmars.model.Photo;

/**
 * Created by Bharat on 10/13/2016.
 */

public class RoverImagesDetailsPresenter implements RoverImagesDetailsContract.UserActionsListener {

    private final RoverImagesDetailsContract.View mDetailsView;
    private boolean isFavorite;
    private Photo detailsPhoto;

    public RoverImagesDetailsPresenter(@NonNull RoverImagesDetailsContract.View detailsView) {
        mDetailsView = detailsView;
    }

    @Override
    public void openDetails(Photo photo) {
        detailsPhoto = photo;
        mDetailsView.showImage(photo.getImgSrc());
        mDetailsView.showInfo(photo);
        isFavorite = RealmDatabase.isFavorite(photo.getId());
        mDetailsView.setFavoritesButton(isFavorite);
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

    @Override
    public void favoriteButtonOnClick() {
        if(!isFavorite) {
            RealmDatabase.insertFavorite(detailsPhoto.getRover().getName(), detailsPhoto.getEarthDate(),
                    detailsPhoto.getSol(), detailsPhoto.getCamera().getName(), detailsPhoto.getImgSrc(), detailsPhoto.getId());
            isFavorite = true;
        }
        else {
            RealmDatabase.removeFavorite(detailsPhoto.getId());
            isFavorite = false;
        }
        mDetailsView.setFavoritesButton(isFavorite);
    }

}
