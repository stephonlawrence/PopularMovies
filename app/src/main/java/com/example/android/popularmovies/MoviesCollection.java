package com.example.android.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stephon on 12/13/15.
 */
public class MoviesCollection extends AsyncTask <MoviesCollection.Sort, Void, ArrayList<HashMap<String,String>>> {
    public enum Sort{
        POPULAR,
        RATINGS
    }

    private String Log_Tag = MoviesCollection.class.getSimpleName();
    public ArrayList<HashMap<String,String>> movies;

    @Override
    protected ArrayList<HashMap<String,String>> doInBackground(Sort... t){
        movies = new ArrayList<HashMap<String,String>>();
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        JSONObject jsonResults = null;
        String search;

        final String MOVIE_POSTER_THUMB_URL = "http://image.tmdb.org/t/p/";
        final String MOVIE_POSTER_THUMB_SIZE = "w185";

        switch (t[0]){
            case POPULAR:
                search = "popularity.desc";
                break;
            case RATINGS:
                search = "vote_average.desc";
            default:
                search = "popularity.desc";
        }

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast

            final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/discover/movie";
            final String API_PARAM = "api_key";
            final String API_KEY = "";
            final String SORT_PARAM = "sort_by";


            Uri uri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(API_PARAM, API_KEY)
                    .appendQueryParameter(SORT_PARAM, search)
                    .build();


            URL url = new URL(uri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonResults = new JSONObject(buffer.toString());
        } catch (IOException e) {
            Log.e(Log_Tag, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(Log_Tag, "Error closing stream", e);
                }
            }
        }
        Log.v(Log_Tag, "---- " + jsonResults.toString());

        try {
            JSONArray results = jsonResults.getJSONArray("results");
            for(int r = 0; r < results.length(); r++){
                HashMap<String, String> c = new HashMap<String, String>();
                c.put("title", results.getJSONObject(r).getString("original_title"));
                c.put("src", MOVIE_POSTER_THUMB_URL + MOVIE_POSTER_THUMB_SIZE + results.getJSONObject(r).getString("poster_path"));
                //Log.v(Log_Tag, "---- " + c.get("title") +": " + c.get("src"));
                movies.add(c);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
            /*
            try {
                String[] data = getWeatherDataFromJson(forecastJsonStr, numDays);

                return data;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            */
        return movies;
    }

}
