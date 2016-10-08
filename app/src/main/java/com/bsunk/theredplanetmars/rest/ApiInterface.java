package com.bsunk.theredplanetmars.rest;

import com.bsunk.theredplanetmars.model.Photos;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Bharat on 10/3/2016.
 */

public interface ApiInterface {

    @GET("curiosity/photos")
    Observable<Photos> getPhotos(@Query("earth_date") String earthDate, @Query("api_key") String apiKey);

}
