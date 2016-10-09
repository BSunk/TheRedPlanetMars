package com.bsunk.theredplanetmars.roverimages;

import android.support.annotation.NonNull;
import android.widget.Toast;

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

    private final RoverImagesContract.View mRoverImagesView;

    public RoverImagesPresenter( @NonNull RoverImagesContract.View roverView) {
        mRoverImagesView = roverView;
    }

    @Override
    public void loadImages(boolean forceUpdate) {
        mRoverImagesView.setProgressIndicator(true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Observable<Photos> call = apiService.getPhotos("2016-9-1", API_KEY);

        call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Photos>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Photos photos) {
                        mRoverImagesView.showImages(photos);
                        mRoverImagesView.setProgressIndicator(false);
                    }
                });
    }

}
