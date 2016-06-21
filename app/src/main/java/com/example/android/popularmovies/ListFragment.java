package com.example.android.popularmovies;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by stephon on 5/12/16.
 */
public class ListFragment extends Fragment {
    GetMoviesAdapter mAdapter;
    MovieThumbnailAdapter thumbs;
    GridView grid;
    GetMoviesApi.SortOrder state;

//    @Override
//    public void onCreate(Bundle savedInstance){
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        //if(savedInstance != null) {

            View view = inflater.inflate(R.layout.fragment_list, container, false);

            mAdapter = new GetMoviesAdapter(getActivity());


            thumbs = new MovieThumbnailAdapter(getActivity(), new ArrayList<MovieData.movie>());

            grid = (GridView) view.findViewById(R.id.GridView);
            grid.setAdapter(thumbs);

            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Intent intent = new Intent(self, DetailsFragment.class);
                    MainActivity.current = thumbs.list.get(position);
                    DetailsFragment details= new DetailsFragment();
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, details)
                            .addToBackStack(null)
                            .commit();

                    //startActivity(intent);
                }
            });

            refresh();
            return view;
        //}
        //return null;



    }

    @Override
    public void onResume(){
        super.onResume();
        refresh(state);
    }

    /* ***** Get Movie Data ***** */
    public void refresh(){
        // should be changed to refresh current sort order and not the default
        mAdapter.getMovies(GetMoviesApi.SortOrder.POPULAR, movieDataCallback);
        state = GetMoviesApi.SortOrder.POPULAR;
    }
    public void refresh(GetMoviesApi.SortOrder order){
        if(order == GetMoviesApi.SortOrder.FAVORITES){
            Favorites.loadMovies(getActivity());
            replace(Favorites.f);
            state = GetMoviesApi.SortOrder.FAVORITES;
        }else {
            mAdapter.getMovies(order, movieDataCallback);
            state = order;
        }
    }

    public void replace(MovieData mlist){
        thumbs.list.clear();
        thumbs.list.addAll(mlist.getResults());
        thumbs.notifyDataSetChanged();
    }

    public void replace(Favorites.Faves mlist){
        thumbs.list.clear();
        thumbs.list.addAll(mlist.getResults());
        thumbs.notifyDataSetChanged();
    }

    private Callback<MovieData> movieDataCallback= new Callback<MovieData>(){

        @Override
        public void success(MovieData data, Response resp){

            replace(data);
            Log.v("MAIN -------- Success", "added all data to thumbs list.");
        }

        @Override
        public void failure(RetrofitError error){
            //should load a error loading text field instead of the grid
            Log.v("MAIN -------- Failure", "Something went wrong! - "+error);
        }
    };
}
