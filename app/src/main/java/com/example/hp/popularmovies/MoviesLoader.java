package com.example.hp.popularmovies;

        import android.content.AsyncTaskLoader;
        import android.content.Context;

        import org.json.JSONException;

        import java.io.IOException;
        import java.util.List;

public class MoviesLoader extends AsyncTaskLoader<List<Movies>> {
    private final String mUrl;

    public MoviesLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movies> loadInBackground() {
        List<Movies> movies = null;
        if (mUrl == null) {
            return null;
        }

        try {
            movies = Utils.fetchData(mUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }
}
