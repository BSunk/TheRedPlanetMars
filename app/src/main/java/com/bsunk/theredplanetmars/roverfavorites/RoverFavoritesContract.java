package com.bsunk.theredplanetmars.roverfavorites;

import com.bsunk.theredplanetmars.model.FavoritePhoto;

import io.realm.RealmResults;

/**
 * Created by Bharat on 2/13/2017.
 */

public interface RoverFavoritesContract {
    interface View {
        void showImages(RealmResults<FavoritePhoto> photos);
        void setToolbarPhotoCount(String count);
        void showEmptyView(boolean isEmpty);
    }
    interface Presenter {
        void loadImages();
    }
}
