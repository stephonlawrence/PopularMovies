package com.example.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public ArrayList<HashMap<String,String>> movieList;
    MovieThumbnailAdapter thumbs;
    GridView grid;
    Fetch collection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<HashMap<String,String>>();
        thumbs = new MovieThumbnailAdapter(MainActivity.this, movieList);

        /*
        HashMap<String, String> c = new HashMap<String, String>();
        c.put("title","First");
        c.put("src", "http://i.imgur.com/BibbYdcl.jpg");
        movieList.add(c);
        */

        grid = (GridView) findViewById(R.id.GridView);
        grid.setAdapter(thumbs);

        refresh();

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new MainActivityFragment())
                    .commit();
        }*/
    }

    public void refresh(MoviesCollection.Sort s){
        movieList.clear();
        thumbs.notifyDataSetChanged();
        collection = new Fetch();

        collection.execute(s);

    }
    public void refresh(){
        movieList.clear();
        thumbs.notifyDataSetChanged();
        collection = new Fetch();

        collection.execute(MoviesCollection.Sort.POPULAR);

    }

    public class Fetch extends MoviesCollection{
        protected void onPostExecute(ArrayList<HashMap<String,String>> l){
            thumbs.list.addAll(l);
            thumbs.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
                refresh(MoviesCollection.Sort.POPULAR);
                break;
            case R.id.action_ratings:
                refresh(MoviesCollection.Sort.RATINGS);
                break;
            default:
                refresh();
        }

        return super.onOptionsItemSelected(item);
    }
}
