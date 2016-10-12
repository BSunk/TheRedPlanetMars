package com.bsunk.theredplanetmars.roverimages;

import android.support.annotation.NonNull;

import com.bsunk.theredplanetmars.model.Photo;
import com.bsunk.theredplanetmars.model.Photos;

/**
 * Created by Bharat on 10/7/2016.
 */

public interface RoverImagesContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showImageDetails(Photo photo);

        void showImages(Photos photo);

        void setRefreshIndicator(boolean active);

        void showListEmpty(boolean isEmpty);

    }

    interface UserActionsListener {

        void loadImages(boolean forceUpdate, int roverID);

        void openPhotoDetails(@NonNull Photo photo);

    }
}
