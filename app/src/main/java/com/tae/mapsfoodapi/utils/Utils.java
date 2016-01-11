package com.tae.mapsfoodapi.utils;

import android.content.Context;
import android.widget.Toast;

import retrofit.RetrofitError;

/**
 * Created by Eduardo on 17/12/2015.
 */
public class Utils {

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToastErrorInRetrofit(Context context, String msg, RetrofitError error) {
        Toast.makeText(context, msg + error.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
