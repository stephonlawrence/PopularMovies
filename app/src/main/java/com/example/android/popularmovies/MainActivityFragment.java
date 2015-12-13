package com.example.android.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import com.squareup.picasso.*;

import org.json.JSONException;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    ImageView mAdapter;
    MovieThumbnailAdapter thumbs;
    ArrayList<HashMap<String,String>> movieList;
    public MainActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        GridView grid = (GridView) root.findViewById(R.id.GridView);
        movieList = new ArrayList<HashMap<String,String>>();

        thumbs = new MovieThumbnailAdapter(getActivity().getBaseContext(), movieList);
        grid.setAdapter(thumbs);
        //grid.setAdapter(new ImageAdapter(getActivity().getBaseContext()));

        refresh(Sort.POPULAR);

        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_popular:
                refresh(Sort.POPULAR);
            //break;
            case R.id.action_ratings:
                refresh(Sort.RATINGS);
        }

        return super.onOptionsItemSelected(item);
    }

    public void refresh(Sort s){
        movieList.clear();
        new FetchMovies().execute(s);
    }

    public enum Sort {
        POPULAR,
        RATINGS
    }

    public class FetchMovies extends AsyncTask<Sort, Void, Void>{
        private String Log_Tag = FetchMovies.class.getSimpleName();
        @Override
        protected Void doInBackground(Sort... t){
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
                    movieList.add(c);
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
            return null;
        }
    }
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return 10;//mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView view = (ImageView) convertView;
            if(view == null){
                view = new ImageView(mContext);
            }
            //"http://image.tmdb.org/t/p/w92/dkMD5qlogeRMiEixC4YNPUvax2T.j"
            Picasso.with(mContext).load("http://i.imgur.com/BibbYdcl.jpg").into(view);
            //imageView.setImageResource(mThumbIds[position]);
            return view;
        }
    }
}
