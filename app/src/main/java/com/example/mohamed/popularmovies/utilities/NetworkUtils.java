package com.example.mohamed.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Mohamed on 5/4/2018.
 */

public class NetworkUtils {

    private static final String STATIC_Movie_URL ="https://yts.am/api/v2/list_movies.json";



    final static String SORT_BY_PARAM = "sort_by";
    final static String SERACH_PARAM="query_term";

    private static final String TAG =NetworkUtils.class.getSimpleName(); ;

    public static URL buildUrl(String sortby) {
        Uri builtUri = Uri.parse(STATIC_Movie_URL).buildUpon()
                .appendQueryParameter(SORT_BY_PARAM,sortby)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static URL buildUrlForSearch(String movieName) {
        Uri builtUri = Uri.parse(STATIC_Movie_URL).buildUpon()
                .appendQueryParameter(SERACH_PARAM,movieName)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
