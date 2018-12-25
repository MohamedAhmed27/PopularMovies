package com.example.mohamed.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mohamed on 6/19/2018.
 */

public class MovieContract  {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.mohamed.popularmovies";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_MOVIE = "movie";


    public static final class MovieEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();
        public static final String TABLE_NAME = "movie";
        public static final String Movie_Id = "movieId";
        public static final String Movie_Name="movieName";
        public static final String Movie_Image="movieImage";
        public static final String Movie_Overview="movieOverview";
        public static final String Movie_VoteAvarage="movieVote";
        public static final String Movie_ReleaseDate="movieRelease";
        public static final String Movie_Trailer_ID="movieTrailer";
        public static final String Movie_BackGround_Image="background";
        public static final String Movie_720P_torrent_link="torrent720";
        public static final String Movie_1080P_torrent_link="torrent1080";



    }
}
