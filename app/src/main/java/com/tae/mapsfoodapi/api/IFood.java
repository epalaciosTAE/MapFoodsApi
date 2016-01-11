package com.tae.mapsfoodapi.api;

import com.tae.mapsfoodapi.constants.Constants;
import com.tae.mapsfoodapi.model.api.place.Place;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Eduardo on 06/01/2016.
 */
public interface IFood {

    @GET("/maps/api/place/nearbysearch/json")
    public void getPlaces(@Query(Constants.URL_QUERY_LOCATION) String latitude,
                          @Query(Constants.URL_QUERY_RADIUS) String radius,
                          @Query(Constants.URL_QUERY_TYPE) String type,
                          @Query(Constants.URL_QUERY_NAME) String name,
                          @Query(Constants.URL_QUERY_API_KEY) String key,
                          Callback<Place> response);
}

