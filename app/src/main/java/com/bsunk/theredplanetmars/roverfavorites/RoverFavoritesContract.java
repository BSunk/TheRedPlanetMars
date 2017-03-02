package com.bsunk.theredplanetmars.roverfavorites;

import com.bsunk.theredplanetmars.model.FavoritePhoto;

import java.util.List;

/**
 * Created by Bharat on 2/13/2017.
 */

public interface RoverFavoritesContract {
    interface View {
        void showImages(List<FavoritePhoto> photos);
        void setToolbarPhotoCount(String count);
        void showEmptyView(boolean isEmpty);
    }
    interface Presenter {
        void loadImages();
    }
}
