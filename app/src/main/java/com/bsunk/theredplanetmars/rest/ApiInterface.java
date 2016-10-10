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
    Observable<Photos> getCuriosityPhotos(@Query("earth_date") String earthDate, @Query("api_key") String apiKey);

    @GET("spirit/photos")
    Observable<Photos> getSpiritPhotos(@Query("earth_date") String earthDate, @Query("api_key") String apiKey);

    @GET("opportunity/photos")
    Observable<Photos> getOpportunityPhotos(@Query("earth_date") String earthDate, @Query("api_key") String apiKey);

}
