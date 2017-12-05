package com.example.hp.popularmovies.Details;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.hp.popularmovies.Utils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;


public class ReviewLoader extends AsyncTaskLoader<List<Reviews>> {

    private final String mUrl;
    public ReviewLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Reviews> loadInBackground() {
        List<Reviews> review = null;
        try {
            review = Utils.fetchReviews(mUrl);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return review;
    }
}
