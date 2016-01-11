package com.tae.mapsfoodapi.model.local;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eduardo on 06/01/2016.
 */
public class PlaceLocation implements Parcelable{

    private double latitude;
    private double longitude;

    public PlaceLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected PlaceLocation(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<PlaceLocation> CREATOR = new Creator<PlaceLocation>() {
        @Override
        public PlaceLocation createFromParcel(Parcel in) {
            return new PlaceLocation(in);
        }

        @Override
        public PlaceLocation[] newArray(int size) {
            return new PlaceLocation[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
