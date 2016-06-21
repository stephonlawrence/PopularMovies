package com.example.android.popularmovies;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends AppCompatActivity {
    GetMoviesAdapter mAdapter;
    MovieThumbnailAdapter thumbs;
    GridView grid;
    static MovieData.movie current;
    GetMoviesApi.SortOrder state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction trans = getFragmentManager().beginTransaction();
        ListFragment list = new ListFragment();
        trans.add(R.id.fragment_container, list)
        .commit();

//        mAdapter = new GetMoviesAdapter(getApplicationContext());
//
//        thumbs = new MovieThumbnailAdapter(MainActivity.this, new ArrayList<MovieData.movie>());
//
//        grid = (GridView) findViewById(R.id.GridView);
//        grid.setAdapter(thumbs);
//        final MainActivity self = this;
//        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //Intent intent = new Intent(self, DetailsFragment.class);
//                MainActivity.current = thumbs.list.get(position);
//
//                //startActivity(intent);
//            }
//        });
//
//        refresh();

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new MainActivityFragment())
                    .commit();
        }*/
    }


//    @Override
//    protected void onRestart(){
//        super.onRestart();
//        refresh(state);
//    }
//
//    /* ***** Get Movie Data ***** */
//    public void refresh(){
//        // should be changed to refresh current sort order and not the default
//        mAdapter.getMovies(GetMoviesApi.SortOrder.POPULAR, movieDataCallback);
//        state = GetMoviesApi.SortOrder.POPULAR;
//    }
//    public void refresh(GetMoviesApi.SortOrder order){
//        if(order == GetMoviesApi.SortOrder.FAVORITES){
//            Favorites.loadMovies(getApplicationContext());
//            replace(Favorites.f);
//            state = GetMoviesApi.SortOrder.FAVORITES;
//        }else {
//            mAdapter.getMovies(order, movieDataCallback);
//            state = order;
//        }
//    }
//
//    public void replace(MovieData mlist){
//        thumbs.list.clear();
//        thumbs.list.addAll(mlist.getResults());
//        thumbs.notifyDataSetChanged();
//    }
//
//    public void replace(Favorites.Faves mlist){
//        thumbs.list.clear();
//        thumbs.list.addAll(mlist.getResults());
//        thumbs.notifyDataSetChanged();
//    }
//
//    private Callback<MovieData> movieDataCallback= new Callback<MovieData>(){
//
//        @Override
//        public void success(MovieData data, Response resp){
//
//            replace(data);
//            Log.v("MAIN -------- Success", "added all data to thumbs list.");
//        }
//
//        @Override
//        public void failure(RetrofitError error){
//            //should load a error loading text field instead of the grid
//            Log.v("MAIN -------- Failure", "Something went wrong! - "+error);
//        }
//    };

    /* ****************************************** ********************************** */


    /* ***** Menu Controls ***** */
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
                //refresh(GetMoviesApi.SortOrder.POPULAR);
                break;
            case R.id.action_ratings:
                //refresh(GetMoviesApi.SortOrder.RATINGS);
                break;
            case R.id.action_favorites:
                //refresh(GetMoviesApi.SortOrder.FAVORITES);
                break;
            default:
                //refresh();
        }

        return super.onOptionsItemSelected(item);
    }
    /* ******************** ************* */
}
