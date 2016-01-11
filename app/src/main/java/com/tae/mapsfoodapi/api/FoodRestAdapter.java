package com.tae.mapsfoodapi.api;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.tae.mapsfoodapi.constants.Constants;
import com.tae.mapsfoodapi.model.api.place.Place;
import com.tae.mapsfoodapi.model.api.place.Result;
import com.tae.mapsfoodapi.model.local.PlaceLocation;
import com.tae.mapsfoodapi.model.local.PlaceModel;
import com.tae.mapsfoodapi.model.local.PlacesModel;
import com.tae.mapsfoodapi.utils.FoodIconUtils;
import com.tae.mapsfoodapi.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Eduardo on 06/01/2016.
 */
public class FoodRestAdapter {

    private static final String TAG = FoodRestAdapter.class.getClass().getSimpleName();
    private IFood iFood;
    private Context context;

    public FoodRestAdapter(Context context) {
        this.context = context;
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Constants.BASE_URL)
                .build();
        iFood = restAdapter.create(IFood.class);
    }

    public void getPlaces(
            String location,
            String radius,
            String type,
            String name) {
        iFood.getPlaces(location, radius, type, name, Constants.API_KEY, new Callback<Place>() {
            @Override
            public void success(Place place, Response response) {
                // TODO handle the result
                final List<PlaceModel> places = new ArrayList<PlaceModel>(place.getResults().size());
                for (final Result result : place.getResults()) {
                    if (!FoodIconUtils.isIconInCache(context, result.getName())) {
                        cacheFoodIcon(result);
                    }
                    places.add(new PlaceModel(
                                    result.getPlaceId(),
                                    result.getName(),
                                    result.getIcon(),
                                    new PlaceLocation(
                                            result.getGeometry().getLocation().getLat(),
                                            result.getGeometry().getLocation().getLng()),
                                    result.getVicinity()
                            )
                    );
                }

                Log.i(TAG, "success: Place 1 from API " + place.getResults().get(0).getName());
                // TODO send broadcast to activity. Add extras if need it
                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(Constants.ACTION_DOWNLOAD_SUCCESS)
                        .putExtra(Constants.EXTRA_PLACES_MODEL, new PlacesModel(places)));
            }

            private void cacheFoodIcon(final Result result) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            FoodIconUtils.cacheFoodIconsInFile(context, Picasso.with(context).load(result.getIcon()).get(), result.getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute();
            }

            @Override
            public void failure(RetrofitError error) {
                handleErrorResponse(error);
            }
        });
    }

    private void handleErrorResponse(RetrofitError error) {
        if (error.getKind().equals(RetrofitError.Kind.HTTP)) {
            Utils.showToastErrorInRetrofit(context, "Http error: ", error);
        } else if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {
            Utils.showToastErrorInRetrofit(context, "Network error: ", error);
        } else if (error.getKind().equals(RetrofitError.Kind.CONVERSION)) {
            Utils.showToastErrorInRetrofit(context, "Conversion error: ", error);
        } else {
            Utils.showToastErrorInRetrofit(context, "Unknown error: ", error);
        }
        LocalBroadcastManager.getInstance(context)
                .sendBroadcast(new Intent(Constants.ACTION_DOWNLOAD_SUCCESS));
    }

}
