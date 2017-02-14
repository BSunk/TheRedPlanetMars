package com.bsunk.theredplanetmars.roverfavorites;

import android.support.annotation.NonNull;

import com.bsunk.theredplanetmars.data.RealmDatabase;
import com.bsunk.theredplanetmars.model.FavoritePhoto;

import io.realm.RealmResults;

/**
 * Created by Bharat on 2/13/2017.
 */

public class RoverFavoritesPresenter implements RoverFavoritesContract.Presenter{

    private final RoverFavoritesContract.View view;

    RoverFavoritesPresenter(@NonNull RoverFavoritesContract.View roverView) {
        view = roverView;
    }

    public void loadImages() {
        RealmResults<FavoritePhoto> result =  RealmDatabase.getFavorites();
        view.setToolbarPhotoCount(String.valueOf(result.size()));
        view.showImages(result);
    }

}
