package com.example.mohamed.popularmovies.utilities;

import android.util.Log;

import com.example.mohamed.popularmovies.model.movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mohamed on 5/4/2018.
 */

public class jsonUtil {
    public static final String TAG_ARRAY="movies";
    public static ArrayList<movie> ParseMoviewithJson(String JSON) throws JSONException {
        ArrayList <movie> movies =new ArrayList<movie>();

        JSONObject main=new JSONObject(JSON);

        JSONObject MAIN=main.getJSONObject("data");
        JSONArray results=MAIN.getJSONArray(TAG_ARRAY);

        ArrayList<String>torrentss=new ArrayList<>();
        for(int i=0;i<results.length();i++)
        {

            torrentss=new ArrayList<>();
            JSONObject object=results.getJSONObject(i);

            JSONArray torrents=object.getJSONArray("torrents");

            for(int j=0;j<torrents.length();j++)
            {
                JSONObject torrentsJSONObject=torrents.getJSONObject(j);
                torrentss.add(torrentsJSONObject.optString("url"));
            }



            movies.add(i,new movie(object.optLong("id"),object.optString("title"),object.optString("large_cover_image"),object.optString("background_image_original"),object.optString("summary")
                    ,object.optString("rating"),object.optString("year"),torrentss,object.optString("yt_trailer_code")));


        }

    return movies;
    }

    }





