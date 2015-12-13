package com.example.android.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stephon on 10/30/15.
 */
public class MovieThumbnailAdapter extends BaseAdapter{
    private final Context context;
    private final Picasso pLoader;
    private final LayoutInflater layFlater;

    /*
    "title" => title
    "src" => image source
     */
    public ArrayList<HashMap<String,String>> list;

    public MovieThumbnailAdapter(Context c, ArrayList<HashMap<String,String>> l) {
        context = c;
        pLoader = Picasso.with(c);
        layFlater = LayoutInflater.from(c);
        list = l;
    }
    public MovieThumbnailAdapter(Context c) {
        context = c;
        pLoader = Picasso.with(c);
        layFlater = LayoutInflater.from(c);
        list = new ArrayList<HashMap<String, String>>();
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public HashMap<String, String> getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup Parent){
        View out = layFlater.inflate(R.layout.movie_item_list_view, null, false);
        if(convertView == null) {
            TextView title = (TextView) out.findViewById(R.id.movieItemTitle);
            ImageView image = (ImageView) out.findViewById(R.id.movieItemImage);
            HashMap<String, String> data = getItem(position);
            if (data.get("title") != null) {
                title.setText(data.get("title"));
            } else {
                title.setText("Untitled - Entry");
            }
            if (data.get("src") != null) {
                pLoader.load(data.get("src")).into(image);
            }
        }else{
            out = (View) convertView;
        }
        return out;
    }
}
