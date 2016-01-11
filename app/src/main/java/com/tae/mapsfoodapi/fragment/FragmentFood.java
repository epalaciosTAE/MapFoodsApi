package com.tae.mapsfoodapi.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.tae.mapsfoodapi.R;
import com.tae.mapsfoodapi.constants.Constants;
import com.tae.mapsfoodapi.listener.OnPlacesLoadedListener;
import com.tae.mapsfoodapi.markers.CustomWindowMarker;
import com.tae.mapsfoodapi.model.local.PlacesModel;
import com.tae.mapsfoodapi.service.FoodIconTask;

import butterknife.ButterKnife;

/**
 * Created by Eduardo on 07/01/2016.
 */
public class FragmentFood extends Fragment implements OnMapReadyCallback,
                                                      GoogleMap.OnInfoWindowClickListener {

    public static final String TAG = FragmentFood.class.getSimpleName();
    private PlacesModel placesModel;
    private GoogleMap mMap;
    private OnPlacesLoadedListener onPlacesLoadedListener;

    public static FragmentFood newInstance (PlacesModel placesModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.ARGS_PLACES_MODEL, placesModel);
        FragmentFood fragment = new FragmentFood();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placesModel = getArguments().getParcelable(Constants.ARGS_PLACES_MODEL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map, container, false);
        ButterKnife.bind(this, view);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap = googleMap;
        mMap.setMyLocationEnabled(true); // TODO why this doesnt work in Nexus 6 v23?¿?¿
//        // Hide the zoom controls as the button panel will cover it.
        mMap.getUiSettings().setZoomControlsEnabled(false);
//        // Setting an info window adapter allows us to change the both the contents and look of the
//        // info window.
        mMap.setInfoWindowAdapter(new CustomWindowMarker(getActivity())); // TODO this enable a custom view for the marker window
        //
//
//        // Set listeners for marker events.  See the bottom of this class for their behavior.
//        mMap.setOnMarkerClickListener(this); // this makes an animation when click in the marker
        mMap.setOnInfoWindowClickListener(this);
//        mMap.setOnMarkerDragListener(this);

        // TODO start asyntask to get the icons and load the marker
        OnPlacesLoadedListener onPlacesLoadedListener = getOnPlacesLoadedListener();
        FoodIconTask iconTask = new FoodIconTask(getActivity(), mMap, onPlacesLoadedListener);
        iconTask.execute(placesModel);

    }

    @Nullable
    private OnPlacesLoadedListener getOnPlacesLoadedListener() {
        try {
            onPlacesLoadedListener = (OnPlacesLoadedListener) getActivity();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return onPlacesLoadedListener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onPlacesLoadedListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }



    /** Demonstrates customizing the info window and/or its contents. */
//    private class CustomInfoWindowAdapter  {
//        // These a both viewgroups containing an ImageView with id "badge" and two TextViews with id
//        // "title" and "snippet".
//        private final View markerWindow;
////        private final View mContents;
//
//        public CustomInfoWindowAdapter(Marker marker) {
//            markerWindow = getActivity().getLayoutInflater().inflate(R.layout.custom_info_window, null);
////            mContents = getActivity().getLayoutInflater().inflate(R.layout.custom_info_contents, null);
//
//            ((ImageView) markerWindow.findViewById(R.id.badge)).setImageResource(android.R.drawable.ic_dialog_alert);
//            TextView titleUi = ((TextView) markerWindow.findViewById(R.id.title));
//            titleUi.setText(marker.getTitle());
//            TextView snippetUi = ((TextView) markerWindow.findViewById(R.id.snippet));
//            snippetUi.setText(marker.getSnippet());
//        }

//        @Override
//        public View getInfoWindow(Marker marker) {
//            render(marker, markerWindow);
//            return markerWindow;
//        }
//
//        @Override
//        public View getInfoContents(Marker marker) {
//            render(marker, mContents);
//            return mContents;
//        }

//        private void render(Marker marker, View view) {
//            ((ImageView) view.findViewById(R.id.badge)).setImageResource(android.R.drawable.ic_dialog_alert); // TODO this will add an icon into the markert window
//
////            String title = marker.getTitle();
////            TextView titleUi = ((TextView) view.findViewById(R.id.title));
////            if (title != null) {
////                // Spannable string allows us to edit the formatting of the text.
////                SpannableString titleText = new SpannableString(title);
////                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
////                titleUi.setText(titleText);
////            } else {
////                titleUi.setText("");
////            }
//
//            String snippet = marker.getSnippet();
//            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
//            if (snippet != null && snippet.length() > 12) {
//                SpannableString snippetText = new SpannableString(snippet);
//                snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
//                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(), 0);
//                snippetUi.setText(snippetText);
//            } else {
//                snippetUi.setText("");
//            }
//        }
//    }
}
