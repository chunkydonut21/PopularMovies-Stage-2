package com.example.hp.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class PopularMoviesContract {


    public static final String CONTENT_AUTHORITY = "com.example.hp.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";


    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();


        public static final String TABLE_NAME = "movies";
        public static final String MOVIE_ID = "id";
        public static final String MOVIE_TITLE = "original_title";
        public static final String MOVIE_POSTER = "poster_path";
        public static final String MOVIE_VOTE_AVERAGE = "vote_average";
        public static final String MOVIE_RELEASE_DATE = "release_date";
        public static final String MOVIE_OVERVIEW = "overview";

    }




}
