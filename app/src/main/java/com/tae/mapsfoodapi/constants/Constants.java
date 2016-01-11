package com.tae.mapsfoodapi.constants;

/**
 * Created by Eduardo on 06/01/2016.
 */
public class Constants {

    public static final String BASE_URL = "https://maps.googleapis.com";
    //    public static final String PLACES_ENDPOINT = "/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&types=food&name=cruise&key=" + Constants.API_KEY;
    public static final String PLACES_ENDPOINT = "/maps/api/place/nearbysearch/json" +
            "json?location={" + Constants.URL_QUERY_LOCATION + "}"+
            "&radius={" + Constants.URL_QUERY_RADIUS + "}" +
            "&types={" + Constants.URL_QUERY_TYPE + "}" +
            "&name={" + Constants.URL_QUERY_NAME + "}" +
            "&key={" + Constants.URL_QUERY_API_KEY + "}";

    public static final String RESTAURANT_ENDPOINT = "";

    public static final String API_KEY = "AIzaSyAP_lX01UK7Pih200_bCNIUtMdfm_bl2kA";
    public static final String DEFAULT_LATITUDE = "-33.8670522";
    public static final String DEFAULT_LONGITUDE= "151.1957362";
    public static final String URL_QUERY_DEFAULT_RADIUS = "500";
    public static final String URL_QUERY_DEFAULT_TYPE = "food";
    public static final String URL_QUERY_DEFAULT_NAME = "cruise";
    public static final String URL_QUERY_DEFAULT_LOCATION = DEFAULT_LATITUDE + "," + DEFAULT_LONGITUDE;

//    public static final String URL_QUERY_LATITUDE = "LAT";
//    public static final String URL_QUERY_LONGITUDE= "LONG";
    public static final String URL_QUERY_LOCATION= "location";
    public static final String URL_QUERY_RADIUS = "radius";
    public static final String URL_QUERY_TYPE = "type";
    public static final String URL_QUERY_NAME = "name";

    public static final String URL_QUERY_API_KEY = "key";
    public static final String ACTION_DOWNLOAD_SUCCESS = "com.tae.mapsfoodapi.SUCCESS";

    public static final String EXTRA_PLACES_MODEL = "extra_places_model";
    public static final String ARGS_PLACES_MODEL = "args_places_model";
    public static final String CACHE_FOLDER = "Food_Icons";

    public static final int PERMISSIONS_REQUEST_FINE_LOCATION = 101;
    public static final String HOCKEY_APP_ID = "aeca347aca7b41e496b59e4d998d71fc";
}
