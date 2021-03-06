package com.bsunk.theredplanetmars.roverfavorites;

import android.support.annotation.NonNull;

import com.bsunk.theredplanetmars.data.RealmFavoritesRepositoryContract;
import com.bsunk.theredplanetmars.model.FavoritePhoto;

import java.util.List;


/**
 * Created by Bharat on 2/13/2017.
 */

public class RoverFavoritesPresenter implements RoverFavoritesContract.Presenter{

    private final RoverFavoritesContract.View view;
    private final RealmFavoritesRepositoryContract realmRepository;

    public RoverFavoritesPresenter(@NonNull RoverFavoritesContract.View roverView, @NonNull RealmFavoritesRepositoryContract realmRepo) {
        view = roverView;
        realmRepository = realmRepo;
    }

    public void loadImages() {
        List<FavoritePhoto> result = realmRepository.getAll();
        view.setToolbarPhotoCount(String.valueOf(result.size()));
        if(result.size()==0) {
            view.showEmptyView(true);
        }
        else {
            view.showImages(result);
        }
    }

}
