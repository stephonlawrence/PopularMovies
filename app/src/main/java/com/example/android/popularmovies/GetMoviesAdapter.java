package com.example.android.popularmovies;

import android.content.Context;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;

/**
 * Created by stephon on 2/29/16.
 */
public class GetMoviesAdapter {
    protected RestAdapter rAdapter;
    protected GetMoviesApi mApi;
    static final String MOVIEDB_URL = "http://api.themoviedb.org";
    static final String API_Key = "";
    private Context c;

    public GetMoviesAdapter(Context x){
        c = x;
        rAdapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(MOVIEDB_URL)
            .build();
        mApi = rAdapter.create(GetMoviesApi.class);
    }

    public void getMovies(GetMoviesApi.SortOrder sortOrder, Callback<MovieData> cb){
        switch(sortOrder){
            case POPULAR:
                mApi.getMovieData(API_Key, GetMoviesApi.Popular, cb);
                break;
            case RATINGS:
                mApi.getMovieData(API_Key, GetMoviesApi.Ratings, cb);
                break;
            case FAVORITES:
                Favorites.loadMovies(c);
                break;
            default:
                mApi.getMovieData(API_Key, GetMoviesApi.Popular, cb);
        }


    }
}
