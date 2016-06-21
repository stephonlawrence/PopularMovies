package com.example.android.popularmovies;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by stephon on 3/25/16.
 */
public class Favorites {

    static final String FavoritesFile = "Favorites.json";
    static Gson gson = new Gson();
    static Faves f = new Faves();

    static boolean addMovie(MovieData.movie m, Context context){
        boolean result = f.addMovie(m);
        if(result)
            write(f, context);
        return result;
    }

    static boolean removeMovie(MovieData.movie m, Context context){
        boolean result = f.removeMovie(m);
        if(result)
            write(f, context);
        return result;
    }



    static void loadMovies(Context c){
        f = read(c);
    }

    static void write(Faves m, Context context){
        FileOutputStream output;
        try{
            output = context.openFileOutput(FavoritesFile, Context.MODE_PRIVATE);
            output.write(gson.toJson(m).getBytes());
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static Faves read(Context context){
        FileInputStream input;
        String ret = "";
        try{
            input = context.openFileInput(FavoritesFile);
            if(input != null) {

                InputStreamReader inReader = new InputStreamReader(input);
                BufferedReader bufferedReader = new BufferedReader(inReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                input.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson.fromJson(ret, Faves.class);
    }

    public static class Faves {
        private ArrayList<MovieData.movie> movies;

        Faves(){}

        public ArrayList<MovieData.movie> getResults(){
            return movies;
        }

        public boolean addMovie(MovieData.movie m){
            boolean found = false;
            if(movies == null) {
                movies = new ArrayList<MovieData.movie>();
            }
            for (int i = 0; i < movies.size(); i++) {
                if (movies.get(i).getId() == m.getId()) {
                    found = true;
                    break;
                }
            }
            if(!found){
                movies.add(m);
            }
            return !found;
        }

        public boolean removeMovie(MovieData.movie m){
            if(movies == null){
                movies = new ArrayList<MovieData.movie>();
            }
            for(int i =0; i < movies.size(); i++){
                if(movies.get(i).getId() == m.getId()){
                    movies.remove(i);
                    return true;
                }
            }
            return false;
        }
    }


}
