package com.example.android.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by stephon on 12/18/15.
 */
public class DetailsFragment extends Fragment {

    ImageView poster;
    ListView list;
    TextView[] text;
    Picasso p;

    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.activity_details, container, false);
        context = getActivity();
        text = new TextView[4];
        p = Picasso.with(getActivity());

        poster = (ImageView) view.findViewById(R.id.poster);
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("DETAILS ------- CLICK", MainActivity.current.getOriginal_title());
                if(!Favorites.addMovie(MainActivity.current, getActivity()))
                    Favorites.removeMovie(MainActivity.current, getActivity());

            }
        });
        //title, release date, movie poster, vote average, and plot synopsis
        text[0] = (TextView) view.findViewById(R.id.details_title);
        text[1] = (TextView) view.findViewById(R.id.details_release);
        text[2] = (TextView) view.findViewById(R.id.details_vote);
        text[3] = (TextView) view.findViewById(R.id.details_plot);
        //29:34
        if(MainActivity.current != null) {
            p.load(MainActivity.current.getPoster_path()).into(poster);

            text[0].setText(MainActivity.current.getOriginal_title());
            text[1].setText("Release Date: " + MainActivity.current.getRelease_date());
            text[2].setText("Vote Average: " + MainActivity.current.getVote_average());
            text[3].setText(MainActivity.current.getOverview());

        }
        return view;

    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        context = getActivity();
//        setContentView(R.layout.activity_details);
//        text = new TextView[4];
//        p = Picasso.with(getApplicationContext());
//
//        poster = (ImageView) findViewById(R.id.poster);
//        poster.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.v("DETAILS ------- CLICK", MainActivity.current.getOriginal_title());
//                if(!Favorites.addMovie(MainActivity.current, getApplicationContext()))
//                    Favorites.removeMovie(MainActivity.current, getApplicationContext());
//
//            }
//        });
//        //title, release date, movie poster, vote average, and plot synopsis
//        text[0] = (TextView) findViewById(R.id.details_title);
//        text[1] = (TextView) findViewById(R.id.details_release);
//        text[2] = (TextView) findViewById(R.id.details_vote);
//        text[3] = (TextView) findViewById(R.id.details_plot);
//
//        p.load(MainActivity.current.getPoster_path()).into(poster);
//
//        text[0].setText(MainActivity.current.getOriginal_title());
//        text[1].setText("Release Date: "+MainActivity.current.getRelease_date());
//        text[2].setText("Vote Average: "+MainActivity.current.getVote_average());
//        text[3].setText(MainActivity.current.getOverview());
//
//    }
}
