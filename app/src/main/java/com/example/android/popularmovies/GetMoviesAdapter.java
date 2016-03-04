package com.example.android.popularmovies;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by stephon on 2/29/16.
 */
public class GetMoviesAdapter {
    protected RestAdapter rAdapter;
    protected GetMoviesApi mApi;
    static final String MOVIEDB_URL = "http://api.themoviedb.org";
    static final String API_Key = "e2de2d779eeeff96aaa73256b6a6fcbd";

    public GetMoviesAdapter(){
        rAdapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(MOVIEDB_URL)
            .build();
        mApi = rAdapter.create(GetMoviesApi.class);
    }

    public void getMovies(GetMoviesApi.SortOrder sortOrder, Callback<MovieData> cb){
        String order;
        switch(sortOrder){
            case POPULAR:
                order = GetMoviesApi.Popular;
                break;
            case RATINGS:
                order = GetMoviesApi.Ratings;
                break;
            default:
                order = GetMoviesApi.Popular;
        }
        mApi.getMovieData(API_Key, order, cb);
    }
}
