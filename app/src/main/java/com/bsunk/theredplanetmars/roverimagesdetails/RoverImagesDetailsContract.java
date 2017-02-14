package com.bsunk.theredplanetmars.roverimagesdetails;

import com.bsunk.theredplanetmars.model.Photo;

/**
 * Created by Bharat on 10/13/2016.
 */

public interface RoverImagesDetailsContract {

    interface View {

        void showImage(String imageURL);
        void showInfo(Photo photo);
        void requestPermissions();
        void showShareErrorToast();
        void onShareItem();
        void setFavoritesButton(boolean isFavorite);
    }

    interface UserActionsListener {

        void openDetails(Photo photo);
        void shareButtonClick();
        void onPermissionResultReturned(int code, int[] grantResults);
        void noPermissionsNeeded();
        void favoriteButtonOnClick();

    }

}
