package com.example.hp.popularmovies.Details;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.popularmovies.BuildConfig;
import com.example.hp.popularmovies.R;
import com.example.hp.popularmovies.SettingsActivity;
import com.example.hp.popularmovies.data.PopularMoviesContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerView rv2;
    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;

    private TextView emptyReviewView;
    private TextView emptyTrailerView;

    public String reviewUrl = "https://api.themoviedb.org/3/movie/";

    private String movieId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        TextView title = (TextView) findViewById(R.id.title);
        TextView releaseDate = (TextView) findViewById(R.id.releaseDate);
        TextView voteAverage = (TextView) findViewById(R.id.vote_average);
        TextView overview = (TextView) findViewById(R.id.overview);
        ImageView imageView = (ImageView) findViewById(R.id.picture);
        final ImageView favorite = (ImageView) findViewById(R.id.favorites);


        title.setText(getIntent().getExtras().getString("Title"));
        releaseDate.setText(getIntent().getExtras().getString("Release"));
        voteAverage.setText(getIntent().getExtras().getString("Vote") + "/10");
        overview.setText(getIntent().getExtras().getString("Overview"));
        Picasso.with(getApplicationContext()).load(getIntent().getExtras().getString("Path")).into(imageView);

        movieId = getIntent().getExtras().getString("Id");


        rv = (RecyclerView) findViewById(R.id.recyclerReview);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(false);

        reviewAdapter = new ReviewAdapter(this, new ArrayList<Reviews>());
        rv.setAdapter(reviewAdapter);

        emptyReviewView = (TextView) findViewById(R.id.empty_review);
        rv.setVisibility(View.INVISIBLE);
        emptyReviewView.setVisibility(View.VISIBLE);


        rv2 = (RecyclerView) findViewById(R.id.recyclerTrailer);
        rv2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv2.setHasFixedSize(true);
        rv2.setNestedScrollingEnabled(false);

        trailerAdapter = new TrailerAdapter(this, new ArrayList<Trailers>());
        rv2.setAdapter(trailerAdapter);

        emptyTrailerView = (TextView) findViewById(R.id.empty_trailer);
        rv2.setVisibility(View.INVISIBLE);
        emptyTrailerView.setVisibility(View.VISIBLE);


        getLoaderManager().initLoader(2, null, reviews);
        getLoaderManager().initLoader(3, null, trailers);


        boolean isFav = isFavorite();

        if (isFav) {
            favorite.setImageResource(R.drawable.fav);
        } else {
            favorite.setImageResource(R.drawable.nofav);
        }
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean inFavorites = isFavorite();
                if (inFavorites) {
                    favorite.setImageResource(R.drawable.nofav);
                    deleteFromFavorites();
                    Toast.makeText(getApplicationContext(), "Removed from Favourites", Toast.LENGTH_SHORT).show();

                } else {
                    favorite.setImageResource(R.drawable.fav);
                    addToFavorites();
                    Toast.makeText(getApplicationContext(), "Added to Favourites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private LoaderManager.LoaderCallbacks<List<Reviews>> reviews = new LoaderManager.LoaderCallbacks<List<Reviews>>() {
        @Override
        public Loader<List<Reviews>> onCreateLoader(int id, Bundle args) {

            String finalUri = reviewUrl + movieId + "/reviews?";
            Uri baseUri = Uri.parse(finalUri);
            Uri.Builder ub = baseUri.buildUpon();
            ub.appendQueryParameter("api_key", BuildConfig.MY_MOVIE_API);

            return new ReviewLoader(getApplicationContext(), ub.toString());
        }

        @Override
        public void onLoadFinished(Loader<List<Reviews>> loader, List<Reviews> data) {
            if (data != null && !data.isEmpty()) {

                reviewAdapter.settingData(data);
                rv.setVisibility(View.VISIBLE);
                emptyReviewView.setVisibility(View.INVISIBLE);
            } else {

                emptyReviewView.setText(R.string.nothing_found);
                emptyReviewView.setVisibility(View.VISIBLE);
                rv.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Reviews>> loader) {
            reviewAdapter.settingData(null);
        }
    };

    private LoaderManager.LoaderCallbacks<List<Trailers>> trailers = new LoaderManager.LoaderCallbacks<List<Trailers>>() {
        @Override
        public Loader<List<Trailers>> onCreateLoader(int id, Bundle args) {
            String finalUri = reviewUrl + movieId + "/videos?";
            Uri baseUri = Uri.parse(finalUri);
            Uri.Builder ub = baseUri.buildUpon();
            ub.appendQueryParameter("api_key", BuildConfig.MY_MOVIE_API);

            return new TrailerLoader(getApplicationContext(), ub.toString());
        }

        @Override
        public void onLoadFinished(Loader<List<Trailers>> loader, List<Trailers> data) {
            if (data != null && !data.isEmpty()) {

                trailerAdapter.settingData(data);
                rv2.setVisibility(View.VISIBLE);
                emptyTrailerView.setVisibility(View.INVISIBLE);

            } else {

                emptyTrailerView.setText(R.string.nothing_found);
                emptyTrailerView.setVisibility(View.VISIBLE);
                rv.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Trailers>> loader) {
            trailerAdapter.settingData(null);
        }
    };


    /*
   * add movie into favorites in the DB
   * */
    private void addToFavorites() {

        ContentValues values = new ContentValues();


        values.put(PopularMoviesContract.MovieEntry.MOVIE_ID, movieId);
        values.put(PopularMoviesContract.MovieEntry.MOVIE_TITLE, getIntent().getExtras().getString("Title"));
        values.put(PopularMoviesContract.MovieEntry.MOVIE_POSTER, getIntent().getExtras().getString("Path"));
        values.put(PopularMoviesContract.MovieEntry.MOVIE_VOTE_AVERAGE, getIntent().getExtras().getString("Vote"));
        values.put(PopularMoviesContract.MovieEntry.MOVIE_RELEASE_DATE, getIntent().getExtras().getString("Release"));
        values.put(PopularMoviesContract.MovieEntry.MOVIE_OVERVIEW, getIntent().getExtras().getString("Overview"));


        getContentResolver().insert(PopularMoviesContract.MovieEntry.CONTENT_URI, values);

    }

    /*
    * delete movie from the favorites in the DB
    * */
    private void deleteFromFavorites() {

        getContentResolver().delete(PopularMoviesContract.MovieEntry.CONTENT_URI, PopularMoviesContract.MovieEntry.MOVIE_ID + " = ? ",
                new String[]{movieId + ""});
    }


    /*
     * check if the movie is already in database
     */

    public boolean isFavorite() {
        boolean favorite = false;
        Cursor cursor = getContentResolver().query(
                PopularMoviesContract.MovieEntry.CONTENT_URI,
                null,
                PopularMoviesContract.MovieEntry.MOVIE_ID + " = " + movieId,
                null,
                null
        );
        if (cursor != null) {
            favorite = cursor.getCount() != 0;
            cursor.close();
        }
        return favorite;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        switch (id) {
            case R.id.settings_menu:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);

                return true;

            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);

                return true;

            case R.id.share_trailer:
                ShareActionProvider shareActionProvider = new ShareActionProvider(this);
                shareActionProvider.setShareIntent(getShareIntent());
                MenuItemCompat.setActionProvider(item, shareActionProvider);

        }

        return super.onOptionsItemSelected(item);

    }


    private Intent getShareIntent() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this trailer!");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "https://youtu.be/" + trailerAdapter.getMyString());
        return intent;

    }

}

