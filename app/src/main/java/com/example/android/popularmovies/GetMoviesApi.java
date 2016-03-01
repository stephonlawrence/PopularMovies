package com.example.android.popularmovies;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by stephon on 2/29/16.
 */
public interface GetMoviesApi {
    enum SortOrder{
        POPULAR,
        RATINGS
    }
    String Popular = "popularity.desc";
    String Ratings = "vote_average.desc";
    @GET("/3/discover/movie")
    void getMovieData(@Query("api_key") String apiKey, @Query("sort_by") String sortKey, Callback<MovieData> callback);
}
