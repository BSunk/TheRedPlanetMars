package com.bsunk.theredplanetmars.roverimagesdetails;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.bsunk.theredplanetmars.data.RealmFavoritesRepository;
import com.bsunk.theredplanetmars.data.RealmFavoritesRepositoryContract;
import com.bsunk.theredplanetmars.model.Photo;

/**
 * Created by Bharat on 10/13/2016.
 */

public class RoverImagesDetailsPresenter implements RoverImagesDetailsContract.UserActionsListener {

    private final RoverImagesDetailsContract.View mDetailsView;
    private boolean isFavorite;
    private Photo detailsPhoto;
    private RealmFavoritesRepositoryContract realmFavoritesRepository;

    public RoverImagesDetailsPresenter(@NonNull RoverImagesDetailsContract.View detailsView, @NonNull RealmFavoritesRepositoryContract realmFavoritesRepositoryContract) {
        mDetailsView = detailsView;
        realmFavoritesRepository = realmFavoritesRepositoryContract;
    }

    @Override
    public void openDetails(Photo photo) {
        detailsPhoto = photo;
        mDetailsView.showImage(photo.getImgSrc());
        mDetailsView.showInfo(photo);
        isFavorite = realmFavoritesRepository.isFavorite(photo.getId());
        mDetailsView.setFavoritesButton(isFavorite);
    }

    @Override
    public void shareButtonClick() {
        mDetailsView.requestPermissions();
    }

    @Override
    public void onPermissionResultReturned(int code, int[] grantResults) {
        if (code==RoverImagesDetails.MY_PERMISSIONS_REQUEST_WRITE_STORAGE) {
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
            realmFavoritesRepository.insert(detailsPhoto.getRover().getName(), detailsPhoto.getEarthDate(),
                    detailsPhoto.getSol(), detailsPhoto.getCamera().getFullName(), detailsPhoto.getImgSrc(), detailsPhoto.getId());
            isFavorite = true;
        }
        else {
            realmFavoritesRepository.remove(detailsPhoto.getId());
            isFavorite = false;
        }
        mDetailsView.setFavoritesButton(isFavorite);
    }

}
