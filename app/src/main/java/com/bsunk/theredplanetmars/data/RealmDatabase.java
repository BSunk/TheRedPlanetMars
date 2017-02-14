package com.bsunk.theredplanetmars.data;

import com.bsunk.theredplanetmars.model.FavoritePhoto;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Bharat on 2/13/2017.
 */

public class RealmDatabase {

    public static void insertFavorite(final String roverName, final String photoDate, final int sol, final String cameraName, final String imageURL, final int id) {
        Realm realm = Realm.getDefaultInstance();
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

    public static boolean isFavorite(int id) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<FavoritePhoto> query = realm.where(FavoritePhoto.class);
        query.equalTo("id", id);
        RealmResults<FavoritePhoto> result = query.findAll();
        return !result.isEmpty();
    }

    public static RealmResults<FavoritePhoto> getFavorites() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<FavoritePhoto> query = realm.where(FavoritePhoto.class);
        return query.findAllAsync().sort("id");
    }

    public static void removeFavorite(final int id) {
        Realm realm = Realm.getDefaultInstance();
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

}
