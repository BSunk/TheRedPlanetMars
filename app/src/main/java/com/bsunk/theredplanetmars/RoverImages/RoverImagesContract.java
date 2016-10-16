package com.bsunk.theredplanetmars.roverimages;

import com.bsunk.theredplanetmars.model.Photos;

/**
 * Created by Bharat on 10/7/2016.
 */

public interface RoverImagesContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showImages(Photos photo);

        void setRefreshIndicator(boolean active);

        void showListEmpty(boolean isEmpty);

        void setToolbarTitle(String title);

        void setToolbarDate(String date);

        void hideToolbarTitle();

        void hideToolbarDate();

        void showToolbarDate();

        void showToolbarTitle();

    }

    interface UserActionsListener {

        void loadImages(boolean forceUpdate, int roverID, int year, int month, int day);

        void openDatePicker();

    }
}
