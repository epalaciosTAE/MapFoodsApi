package com.tae.mapsfoodapi.model.local;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eduardo on 06/01/2016.
 */
public class PlaceModel implements Parcelable{

    private String placeId;
    private String name;
    private String icon;
    private PlaceLocation location;
    private String vicinity;

    public PlaceModel(String placeId, String name, String icon, PlaceLocation location, String vicinity) {
        this.placeId = placeId;
        this.name = name;
        this.icon = icon;
        this.location = location;
        this.vicinity = vicinity;
    }

    protected PlaceModel(Parcel in) {
        placeId = in.readString();
        name = in.readString();
        icon = in.readString();
        location = in.readParcelable(PlaceLocation.class.getClassLoader());
        vicinity = in.readString();
    }

    public static final Creator<PlaceModel> CREATOR = new Creator<PlaceModel>() {
        @Override
        public PlaceModel createFromParcel(Parcel in) {
            return new PlaceModel(in);
        }

        @Override
        public PlaceModel[] newArray(int size) {
            return new PlaceModel[size];
        }
    };

    public String getPlaceId() {
        return placeId;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getVicinity() {
        return vicinity;
    }

    public PlaceLocation getLocation() {
        return location;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeId);
        dest.writeString(name);
        dest.writeString(icon);
        dest.writeParcelable(location, flags);
        dest.writeString(vicinity);
    }
}
