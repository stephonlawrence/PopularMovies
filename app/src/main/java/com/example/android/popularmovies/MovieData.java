package com.example.android.popularmovies;

import java.util.ArrayList;

/**
 * Created by stephon on 2/29/16.
 */
public class MovieData {
    private int page;
    private ArrayList<movie> results;

    private static final String MOVIE_POSTER_THUMB_URL = "http://image.tmdb.org/t/p/";
    private static final String MOVIE_POSTER_THUMB_SIZE = "w185";

    public MovieData(){}

    public MovieData(int page){
        this.page = page;
    }

    public ArrayList<movie> getResults(){
        return results;
    }

    public void setPage(int page){
        this.page = page;
    }

    public int getPage(){
        return this.page;
    }

    class movie {
        private int id;
        private String original_title;
        private String poster_path;
        private String release_date;
        private String vote_average;
        private String overview;

        public movie(){}

        public movie(int id, String original_title, String poster_path, String release_date, String vote_average, String overview){
            this.id = id;
            this.original_title = original_title;
            this.poster_path = poster_path;
            this.release_date = release_date;
            this.vote_average = vote_average;
            this.overview = overview;
        }

        public void setId(int id){
            this.id = id;
        }

        public int getId(){
            return this.id;
        }

        public void setOriginal_title(String original_title){
            this.original_title = original_title;
        }

        public String getOriginal_title(){
            return this.original_title;
        }

        public void setPoster_path(String poster_path){
            this.poster_path = poster_path;
        }

        public String getPoster_path(){
            return MOVIE_POSTER_THUMB_URL + MOVIE_POSTER_THUMB_SIZE + this.poster_path;
        }

        public void setRelease_date(String release_date){
            this.release_date = release_date;
        }

        public String getRelease_date(){
            return this.release_date;
        }

        public void setVote_average(String vote_average){
            this.vote_average = vote_average;
        }

        public String getVote_average(){
            return this.vote_average;
        }

        public void setOverview(String overview){
            this.overview = overview;
        }

        public String getOverview(){
            return this.overview;
        }

    }

}