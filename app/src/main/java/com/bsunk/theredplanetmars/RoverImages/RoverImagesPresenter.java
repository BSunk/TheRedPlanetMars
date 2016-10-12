package com.bsunk.theredplanetmars.roverimages;

import android.support.annotation.NonNull;
import android.util.Log;

import com.bsunk.theredplanetmars.model.Photo;
import com.bsunk.theredplanetmars.model.Photos;
import com.bsunk.theredplanetmars.rest.ApiClient;
import com.bsunk.theredplanetmars.rest.ApiInterface;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bsunk.theredplanetmars.BuildConfig.API_KEY;

/**
 * Created by Bharat on 10/7/2016.
 */

public class RoverImagesPresenter implements RoverImagesContract.UserActionsListener {

    public static int CURIOSITY = 0;
    public static int OPPORTUNITY = 1;
    public static int SPIRIT = 2;
    public static String ROVER_KEY = "rover_key";

    private final RoverImagesContract.View mRoverImagesView;

    public RoverImagesPresenter( @NonNull RoverImagesContract.View roverView) {
        mRoverImagesView = roverView;
    }

    @Override
    public void loadImages(final boolean forceUpdate, int roverID) {
        if(!forceUpdate) {
        mRoverImagesView.setProgressIndicator(true); }

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Observable<Photos> call = apiService.getCuriosityPhotos("2016-9-1", API_KEY);

        switch (roverID) {
            case 0:
                call = apiService.getCuriosityPhotos("2016-9-1", API_KEY);
                break;
            case 1:
                call = apiService.getOpportunityPhotos("2016-9-1", API_KEY);
                break;
            case 2:
                call = apiService.getSpiritPhotos("2016-9-1", API_KEY);
                break;
        }

        call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Photos>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        Log.v("LOG", e.toString());
                        mRoverImagesView.showListEmpty(true);
                        mRoverImagesView.setProgressIndicator(false);
                        if(forceUpdate){ mRoverImagesView.setRefreshIndicator(false);}
                    }
                    @Override
                    public void onNext(Photos photos) {
                        mRoverImagesView.showListEmpty(false);
                        mRoverImagesView.showImages(photos);
                        mRoverImagesView.setProgressIndicator(false);
                        if(forceUpdate){ mRoverImagesView.setRefreshIndicator(false);}
                    }
                });
    }

    @Override
    public void openPhotoDetails(@NonNull Photo photo) {
        mRoverImagesView.showImageDetails(photo);
    }

}
