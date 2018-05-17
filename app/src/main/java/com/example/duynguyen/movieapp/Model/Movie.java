package com.example.duynguyen.movieapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by duynguyen on 5/15/18.
 */

public class Movie {
    @SerializedName("poster_path")
    private String posterPath;

    public Movie (String url){
        posterPath = url;
    }

    public String getPoster_path() {
        return posterPath;
    }



}
