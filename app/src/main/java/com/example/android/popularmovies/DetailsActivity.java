package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by stephon on 12/18/15.
 */
public class DetailsActivity extends AppCompatActivity {

    ImageView poster;
    ListView list;
    TextView[] text;
    Picasso p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        text = new TextView[4];
        p = Picasso.with(getApplicationContext());

        poster = (ImageView) findViewById(R.id.poster);
        //title, release date, movie poster, vote average, and plot synopsis
        text[0] = (TextView) findViewById(R.id.details_title);
        text[1] = (TextView) findViewById(R.id.details_release);
        text[2] = (TextView) findViewById(R.id.details_vote);
        text[3] = (TextView) findViewById(R.id.details_plot);

        p.load(MainActivity.current.get("src")).into(poster);

        text[0].setText(MainActivity.current.get("title"));
        text[1].setText(MainActivity.current.get("releaseDate"));
        text[2].setText(MainActivity.current.get("voteAverage"));
        text[3].setText(MainActivity.current.get("plot"));

    }
}
