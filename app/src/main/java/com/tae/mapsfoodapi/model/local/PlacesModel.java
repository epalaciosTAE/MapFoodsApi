package com.tae.mapsfoodapi.model.local;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Eduardo on 06/01/2016.
 */
public class PlacesModel implements Parcelable {

    private List<PlaceModel> places;

    public PlacesModel () {

    }

    public PlacesModel(List<PlaceModel> places) {
        this.places = places;
    }

    protected PlacesModel(Parcel in) {
    }

    public static final Creator<PlacesModel> CREATOR = new Creator<PlacesModel>() {
        @Override
        public PlacesModel createFromParcel(Parcel in) {
            return new PlacesModel(in);
        }

        @Override
        public PlacesModel[] newArray(int size) {
            return new PlacesModel[size];
        }
    };

    public List<PlaceModel> getPlaces() {
        return places;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
