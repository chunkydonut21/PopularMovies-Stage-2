package com.example.hp.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class PopularMoviesDbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "popularMovies.db";

    public PopularMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + PopularMoviesContract.MovieEntry.TABLE_NAME + " (" +
                PopularMoviesContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PopularMoviesContract.MovieEntry.MOVIE_ID + " TEXT UNIQUE NOT NULL," +
                PopularMoviesContract.MovieEntry.MOVIE_TITLE + " TEXT NOT NULL," +
                PopularMoviesContract.MovieEntry.MOVIE_POSTER + " TEXT NOT NULL," +
                PopularMoviesContract.MovieEntry.MOVIE_VOTE_AVERAGE + " TEXT NOT NULL," +
                PopularMoviesContract.MovieEntry.MOVIE_RELEASE_DATE + " TEXT NOT NULL," +
                PopularMoviesContract.MovieEntry.MOVIE_OVERVIEW + " TEXT NOT NULL," +
                "UNIQUE (" + PopularMoviesContract.MovieEntry.MOVIE_ID + ") ON CONFLICT IGNORE" +
                " );";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PopularMoviesContract.MovieEntry.TABLE_NAME);
        onCreate(db);

    }
}
