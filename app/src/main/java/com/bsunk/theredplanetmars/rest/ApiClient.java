package com.bsunk.theredplanetmars.rest;


import com.bsunk.theredplanetmars.model.Photos;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import static com.bsunk.theredplanetmars.BuildConfig.API_KEY;

/**
 * Created by Bharat on 10/3/2016.
 */

public class ApiClient {

    //URL to NASA Mars Rover API
    private static final String BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/";
    private static Retrofit retrofit = null;

    private static Retrofit getClient() {
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Observable<Photos> getPhotosFromAPI(int id, String date) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        switch (id){
            case 0:
                return apiService.getCuriosityPhotos(date, API_KEY);
            case 1:
                return apiService.getOpportunityPhotos(date, API_KEY);
            case 2:
                return apiService.getSpiritPhotos(date, API_KEY);
        }
        return null;
    }

}
