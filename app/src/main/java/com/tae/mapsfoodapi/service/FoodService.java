package com.tae.mapsfoodapi.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.tae.mapsfoodapi.api.FoodRestAdapter;
import com.tae.mapsfoodapi.constants.Constants;

/**
 * Created by Eduardo on 06/01/2016.
 */
public class FoodService extends IntentService {
    private static final String TAG = FoodService.class.getClass().getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public FoodService() {
        super(TAG);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, FoodService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        FoodRestAdapter foodRestAdapter = new FoodRestAdapter(getApplicationContext());
        foodRestAdapter.getPlaces(
                Constants.URL_QUERY_DEFAULT_LOCATION, //FIXME this is harcoded ex: "lat,long" use StringBuilder
                Constants.URL_QUERY_DEFAULT_RADIUS,
                Constants.URL_QUERY_DEFAULT_TYPE,
                Constants.URL_QUERY_DEFAULT_NAME);
    }
}
