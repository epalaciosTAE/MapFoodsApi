package com.tae.mapsfoodapi.manger;

import android.content.Context;

import com.tae.mapsfoodapi.model.local.PlaceModel;

import java.util.List;

/**
 * Created by Eduardo on 06/01/2016.
 */
public class PlaceManager {

    private Context context;
    private static PlaceManager instance;
    private List<PlaceModel> places;

    private PlaceManager() {

    }

    public static PlaceManager getInstance(Context context) {
        if (instance == null) {
            instance = new PlaceManager();
        }
        return instance;
    }

    public List<PlaceModel> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlaceModel> places) {
        this.places = places;
    }
}
