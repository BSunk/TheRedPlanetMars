package com.bsunk.theredplanetmars.roverimagesdetails;

import com.bsunk.theredplanetmars.model.Photo;

/**
 * Created by Bharat on 10/13/2016.
 */

public interface RoverImagesDetailsContract {

    public interface View {

        void showImage(String imageURL);

        void showInfo(Photo photo);

    }

    public interface UserActionsListener {

        void openDetails(Photo photo);

    }

}
