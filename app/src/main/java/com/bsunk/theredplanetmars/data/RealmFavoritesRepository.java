package com.bsunk.theredplanetmars.data;

import com.bsunk.theredplanetmars.model.FavoritePhoto;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Bharat on 2/13/2017.
 */

public class RealmFavoritesRepository implements RealmFavoritesRepositoryContract{

    private Realm realm = Realm.getDefaultInstance();

    @Override
    public void insert(final String roverName, final String photoDate, final int sol, final String cameraName, final String imageURL, final int id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                FavoritePhoto fav = realm.createObject(FavoritePhoto.class, id);
                fav.setRoverName(roverName);
                fav.setPhotoDate(photoDate);
                fav.setMartianSol(sol);
                fav.setCameraName(cameraName);
                fav.setImageURL(imageURL);
            }
        });
    }

    @Override
    public void remove(final int id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmQuery<FavoritePhoto> query = realm.where(FavoritePhoto.class);
                query.equalTo("id", id);
                RealmResults<FavoritePhoto> result = query.findAll();
                result.deleteAllFromRealm();
            }
        });
    }

    @Override
    public List<FavoritePhoto> getAll() {
        RealmQuery<FavoritePhoto> query = realm.where(FavoritePhoto.class);
        return query.findAllAsync().sort("photoDate");
    }

    @Override
    public boolean isFavorite(int id) {
        RealmQuery<FavoritePhoto> query = realm.where(FavoritePhoto.class);
        query.equalTo("id", id);
        RealmResults<FavoritePhoto> result = query.findAll();
        return !result.isEmpty();
    }

}
