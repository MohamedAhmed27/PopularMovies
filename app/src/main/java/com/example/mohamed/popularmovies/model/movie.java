package com.example.mohamed.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mohamed on 5/4/2018.
 */

public class movie implements Serializable {
    private long movie_id;
    private String moveName;
    private String image;
    private String overview;
    private String voteAvarage;
    private String releaseDate;
    private String trailer_id;
    private String backGround;
    private ArrayList<String>torrents;



    public movie()
    {

    }
    public movie(long movie_id, String moveName, String image,String backGround, String overview, String voteAvarage, String releaseDate,ArrayList<String>torrents ,String trailer_id) {
        this.movie_id=movie_id;
        this.moveName = moveName;
        this.image = image;
        this.overview = overview;
        this.voteAvarage = voteAvarage;
        this.releaseDate = releaseDate;
        this.torrents=torrents;
        this.trailer_id=trailer_id;
        this.backGround=backGround;


    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }

    public ArrayList<String> getTorrents() {
        return torrents;
    }

    public void setTorrents(ArrayList<String> torrents) {
        this.torrents = torrents;
    }

    public void setTrailer_id(String trailer_id) {
        this.trailer_id = trailer_id;
    }

    public String getTrailer_id() {
        return trailer_id;
    }


    public long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(long movie_id) {
        this.movie_id = movie_id;
    }

    public String getMoveName() {
        return moveName;
    }

    public void setMoveName(String moveName) {
        this.moveName = moveName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteAvarage() {
        return voteAvarage;
    }

    public void setVoteAvarage(String voteAvarage) {
        this.voteAvarage = voteAvarage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }



}
