package com.example.hp.popularmovies.Details;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.hp.popularmovies.Utils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class TrailerLoader extends AsyncTaskLoader<List<Trailers>> {

    private final String mUrl;
    public TrailerLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Trailers> loadInBackground() {
        List<Trailers> trailers = null;
        try {
            trailers = Utils.fetchTrailers(mUrl);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailers;
    }
}
