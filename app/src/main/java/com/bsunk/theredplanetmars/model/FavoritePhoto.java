package com.bsunk.theredplanetmars.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Bharat on 2/13/2017.
 */

public class FavoritePhoto extends RealmObject {

    private String roverName;
    private String photoDate;
    private int martianSol;
    private String cameraName;
    private String imageURL;

    @PrimaryKey
    private int id;

    public FavoritePhoto(){}

    public FavoritePhoto(String roverName, String photoDate, int martianSol, String cameraName, String imageURL, int id) {
        this.roverName = roverName;
        this.photoDate = photoDate;
        this.martianSol = martianSol;
        this.cameraName = cameraName;
        this.imageURL = imageURL;
        this.id = id;
    }

    public String getRoverName() {
        return roverName;
    }

    public void setRoverName(String roverName) {
        this.roverName = roverName;
    }

    public String getPhotoDate() {
        return photoDate;
    }

    public void setPhotoDate(String photoDate) {
        this.photoDate = photoDate;
    }

    public int getMartianSol() {
        return martianSol;
    }

    public void setMartianSol(int martianSol) {
        this.martianSol = martianSol;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
