package com.example.android.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    static final String MOVIE_POSTER_THUMB_URL = "http://image.tmdb.org/t/p/";
    static final String MOVIE_POSTER_THUMB_SIZE = "w185";

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




    //helper
    public static ArrayList<HashMap<String, String>> sortData(MovieData data){
        ArrayList<HashMap<String, String>> nData = new ArrayList<>();
        List<MovieData.movie> res = data.getResults();
        for(int k = 0; k < data.getResults().size(); k++){
            HashMap<String, String> c = new HashMap<String, String>();
            c.put("title", res.get(k).getOriginal_title());
            c.put("src", MOVIE_POSTER_THUMB_URL + MOVIE_POSTER_THUMB_SIZE + res.get(k).getPoster_path());
            //Log.v(Log_Tag, "---- " + c.get("title") +": " + c.get("src"));
            c.put("releaseDate", res.get(k).getRelease_date());
            c.put("voteAverage", res.get(k).getVote_average());
            c.put("plot", res.get(k).getOverview());
            //title, release date, movie poster, vote average, and plot synopsis
            nData.add(c);
        }
        /*
        try {
            JSONArray results = (new JSONObject(data)).getJSONArray("results");
            for(int r = 0; r < results.length(); r++){
                HashMap<String, String> c = new HashMap<String, String>();
                c.put("title", results.getJSONObject(r).getString("original_title"));
                c.put("src", MOVIE_POSTER_THUMB_URL + MOVIE_POSTER_THUMB_SIZE + results.getJSONObject(r).getString("poster_path"));
                //Log.v(Log_Tag, "---- " + c.get("title") +": " + c.get("src"));
                c.put("releaseDate", results.getJSONObject(r).getString("release_date"));
                c.put("voteAverage", results.getJSONObject(r).getString("vote_average"));
                c.put("plot", results.getJSONObject(r).getString("overview"));
                //title, release date, movie poster, vote average, and plot synopsis
                nData.add(c);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        return nData;
    }
}
