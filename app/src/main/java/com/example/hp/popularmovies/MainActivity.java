package com.example.hp.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * PLEASE ADD YOUR API IN GRADLE.PROPERTIES IN VARIABLE MyMovieApiKey
 */

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movies>> {

    private static final String moviesUrl = "https://api.themoviedb.org/3/movie/";

    private MoviesAdapter moviesAdapter;
    private TextView emptyView;
    private RecyclerView rv;


    public static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rv = (RecyclerView) findViewById(R.id.rv);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            rv.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            rv.setLayoutManager(new GridLayoutManager(this, 4));
        }


        rv.setHasFixedSize(true);

        moviesAdapter = new MoviesAdapter(this, new ArrayList<Movies>());
        rv.setAdapter(moviesAdapter);


        emptyView = (TextView) findViewById(R.id.empty_view);
        rv.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.VISIBLE);


        //check for connection
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(1, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            emptyView.setText(R.string.no_connection);

        }

    }


    @Override
    public Loader<List<Movies>> onCreateLoader(int i, Bundle bundle) {


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String sortBy = sharedPreferences.getString(getString(R.string.key_sort_by), getString(R.string.default_value));

        if(sortBy.equals("favourites")){
            Intent intent = new Intent(this, Favourites.class);
            startActivity(intent);
        }
        String finalUrl = moviesUrl + sortBy + "?";

        Uri baseUri = Uri.parse(finalUrl);
        Uri.Builder ub = baseUri.buildUpon();
        ub.appendQueryParameter("api_key", BuildConfig.MY_MOVIE_API);

        return new MoviesLoader(this, ub.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> movies) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);


        if (movies != null && !movies.isEmpty()) {

            moviesAdapter.setWeatherData(movies);

            rv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.INVISIBLE);
        } else {

            emptyView.setText(R.string.nothing_found);
            emptyView.setVisibility(View.VISIBLE);

            rv.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {
        moviesAdapter.setWeatherData(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);

                return true;
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(1, null, this);
    }
    public void onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true);
    }


}

