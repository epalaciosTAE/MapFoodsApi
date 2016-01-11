package com.tae.mapsfoodapi.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.tae.mapsfoodapi.constants.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Eduardo on 07/01/2016.
 */
public class FoodIconUtils {

    public static void cacheFoodIconsInFile(Context context, Bitmap bitmap, String name) {
        FileOutputStream out = null;
        File cachePath = createCacheFolder(context);
        File file = null;
        try {
            file = new File(cachePath, name);
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String loadFoodIconFromCache(Context context, String iconName) {
        File dir = new File(context.getCacheDir(), Constants.CACHE_FOLDER);
        return new File(dir, iconName).getAbsolutePath();
    }

    public static boolean isIconInCache(Context context, String iconName) {
        File dir = new File(context.getCacheDir(), Constants.CACHE_FOLDER);
        return new File(dir, iconName).exists();
    }



    @NonNull
    private static File createCacheFolder(Context context) {
        File cacheDir = new File(context.getCacheDir(), Constants.CACHE_FOLDER);// file dir - folder name
        cacheDir.mkdir();
        return cacheDir;
    }
}
