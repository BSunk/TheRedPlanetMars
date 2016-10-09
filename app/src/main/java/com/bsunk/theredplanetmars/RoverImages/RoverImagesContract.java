package com.bsunk.theredplanetmars.roverimages;

import com.bsunk.theredplanetmars.model.Photos;

/**
 * Created by Bharat on 10/7/2016.
 */

public interface RoverImagesContract {

    interface View {

        void setProgressIndicator(boolean active);

       // void showImageDetails(String imageID);

        void showImages(Photos photos);

    }

    interface UserActionsListener {

        void loadImages(boolean forceUpdate);

    }
}
