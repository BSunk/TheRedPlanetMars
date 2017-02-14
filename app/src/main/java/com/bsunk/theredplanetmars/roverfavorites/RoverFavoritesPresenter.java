package com.bsunk.theredplanetmars.roverfavorites;

import android.support.annotation.NonNull;

/**
 * Created by Bharat on 2/13/2017.
 */

public class RoverFavoritesPresenter implements RoverFavoritesContract.Presenter{

    private final RoverFavoritesContract.View view;

    public RoverFavoritesPresenter(@NonNull RoverFavoritesContract.View roverView) {
        view = roverView;
    }



}
