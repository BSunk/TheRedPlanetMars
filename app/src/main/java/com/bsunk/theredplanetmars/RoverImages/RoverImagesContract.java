package com.bsunk.theredplanetmars.roverimages;

import com.bsunk.theredplanetmars.model.Photos;

/**
 * Created by Bharat on 10/7/2016.
 */

public interface RoverImagesContract {

    interface View {

        void showImages(Photos photo);

        void setRefreshIndicator(boolean active);

        void setToolbarPhotoCount(String count);

        void showListEmpty(boolean isEmpty);

        void setToolbarDate(String date);

        void showToolbarDate();

        void showList();

        void hideList();

    }

    interface UserActionsListener {

        void loadImages(boolean forceUpdate, int roverID, int year, int month, int day);

        public void onDestroy();

    }
}
