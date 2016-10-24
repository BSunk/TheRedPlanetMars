package com.bsunk.theredplanetmars.roverimages;

import android.support.annotation.NonNull;
import android.util.Log;

import com.bsunk.theredplanetmars.R;
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

     static int CURIOSITY = 0;
     static int OPPORTUNITY = 1;
     static int SPIRIT = 2;
     static String ROVER_KEY = "rover_key";

    private final RoverImagesContract.View mRoverImagesView;

     RoverImagesPresenter( @NonNull RoverImagesContract.View roverView) {
        mRoverImagesView = roverView;
    }

    @Override
    public void loadImages(final boolean forceUpdate, int roverID, int year, int month, int day) {
        mRoverImagesView.setRefreshIndicator(true);
        mRoverImagesView.showListEmpty(false);
        mRoverImagesView.hideList();
        mRoverImagesView.setToolbarTitle(roverID);

        final String date = buildDate(year, month, day);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Observable<Photos> call = apiService.getCuriosityPhotos(date, API_KEY);

        switch (roverID) {
            case 0:
                call = apiService.getCuriosityPhotos(date, API_KEY);
                break;
            case 1:
                call = apiService.getOpportunityPhotos(date, API_KEY);
                break;
            case 2:
                call = apiService.getSpiritPhotos(date, API_KEY);
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
                        mRoverImagesView.setToolbarDate(" " + date);
                        mRoverImagesView.setRefreshIndicator(false);
                        mRoverImagesView.setToolbarPhotoCount("");
                    }
                    @Override
                    public void onNext(Photos photos) {
                       // mRoverImagesView.setToolbarTitleText(photos.getPhotos().get(0).getRover().getName());
                        mRoverImagesView.setToolbarDate(" " + photos.getPhotos().get(0).getEarthDate());
                        mRoverImagesView.showToolbarDate();
                        mRoverImagesView.showList();
                        mRoverImagesView.showImages(photos);
                        mRoverImagesView.setRefreshIndicator(false);
                        mRoverImagesView.setToolbarPhotoCount(Integer.toString(photos.getPhotos().size()));

                    }
                });
    }

    private String buildDate(int year, int month, int day) {
        return year + "-" + month + "-" + day;
    }

}
