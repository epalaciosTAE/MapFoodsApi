package com.tae.mapsfoodapi.service;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tae.mapsfoodapi.listener.OnPlacesLoadedListener;
import com.tae.mapsfoodapi.markers.CustomWindowMarker;
import com.tae.mapsfoodapi.model.local.PlaceModel;
import com.tae.mapsfoodapi.model.local.PlacesModel;
import com.tae.mapsfoodapi.utils.FoodIconUtils;

/**
 * Created by Eduardo on 07/01/2016.
 */
public class FoodIconTask extends AsyncTask<PlacesModel, Void, PlacesModel> {

    private Context context;
    private GoogleMap map;
    private OnPlacesLoadedListener onPlacesLoadedListener;


    public FoodIconTask(Context context, GoogleMap map, OnPlacesLoadedListener onPlacesLoadedListener) {
        this.context = context;
        this.map = map;
        this.onPlacesLoadedListener = onPlacesLoadedListener;
    }

    @Override
    protected PlacesModel doInBackground(PlacesModel... params) {
        return getIconsPath(params[0]);
    }

    private PlacesModel getIconsPath (PlacesModel placesModel) {
        for (PlaceModel placeModel : placesModel.getPlaces()) {
            if (FoodIconUtils.isIconInCache(context, placeModel.getName())) {
                placeModel.setIcon(FoodIconUtils.loadFoodIconFromCache(context, placeModel.getName()));
            }
        }
        return placesModel;
    }

    @Override
    protected void onPostExecute(PlacesModel placesModel) {
        super.onPostExecute(placesModel);
        for (PlaceModel placeModel : placesModel.getPlaces()) {
            final LatLng latLng = new LatLng(placeModel.getLocation().getLatitude(), placeModel.getLocation().getLongitude());
            Marker marker = map.addMarker(new MarkerOptions() // here we dont need the marker, deleted if its not going to have any use
                    .position(latLng)
                    .title(placeModel.getName())
                    .snippet(placeModel.getVicinity())
                    .icon(BitmapDescriptorFactory.fromPath(FoodIconUtils.loadFoodIconFromCache(context, placeModel.getName()))));
            map.moveCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionInMap(latLng)));
        }
        onPlacesLoadedListener.arePlacesLoaded(true);

    }

    @NonNull
    private CameraPosition getCameraPositionInMap(LatLng latLng) {
        return new CameraPosition.Builder()
                .target(latLng)
                .zoom(14)
                .bearing(0)  // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera
                .build();
    }

}
