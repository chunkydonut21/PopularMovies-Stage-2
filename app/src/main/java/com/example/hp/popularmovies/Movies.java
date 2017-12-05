package com.example.hp.popularmovies;


public class Movies {

    private String mTitle;
    private String mOverview;
    private String mVote;
    private String mLanguage;
    private String mPath;
    private String mRelease;
    private String mMovieId;


    public Movies(String title, String overview, String vote, String language, String path, String release, String id) {
        mTitle = title;
        mOverview = overview;
        mVote = vote;
        mLanguage = language;
        mPath = path;
        mRelease = release;
        mMovieId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getVote() {
        return mVote;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public String getPath() {
        return mPath;
    }

    public String getRelease() {
        return mRelease;
    }

    public String getMovieId() {
        return mMovieId;
    }
}
