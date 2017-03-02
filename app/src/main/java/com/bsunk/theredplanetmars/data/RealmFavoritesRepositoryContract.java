package com.bsunk.theredplanetmars.data;

import com.bsunk.theredplanetmars.model.FavoritePhoto;

import java.util.List;


/**
 * Created by Bharat on 2/26/2017.
 */

public interface RealmFavoritesRepositoryContract {

    void insert(final String roverName, String photoDate, int sol, String cameraName, String imageURL, int id);
    void remove(final int id);
    List<FavoritePhoto> getAll();
    boolean isFavorite(int id);

}
