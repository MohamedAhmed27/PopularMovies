package com.example.mohamed.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.mohamed.popularmovies.data.MovieContract.*;

/**
 * Created by Mohamed on 6/19/2018.
 */

public class MovieDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 7;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieEntry.Movie_Name + " TEXT NOT NULL, " +
                MovieEntry.Movie_Id+ " INTEGER NOT NULL, " +
                MovieEntry.Movie_Image+ " TEXT NOT NULL, " +
                MovieEntry.Movie_Overview + " TEXT NOT NULL, " +
                MovieEntry.Movie_VoteAvarage + " TEXT NOT NULL, " +
                MovieEntry.Movie_Trailer_ID + " TEXT NOT NULL, " +
                MovieEntry.Movie_BackGround_Image+ " TEXT NOT NULL, " +
                MovieEntry.Movie_720P_torrent_link+ " TEXT, " +
                MovieEntry.Movie_1080P_torrent_link+ " TEXT, " +
                MovieEntry.Movie_ReleaseDate + " TEXT NOT NULL " + "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
