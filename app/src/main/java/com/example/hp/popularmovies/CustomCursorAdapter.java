package com.example.hp.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hp.popularmovies.Details.DetailActivity;
import com.example.hp.popularmovies.data.PopularMoviesContract;
import com.squareup.picasso.Picasso;

public class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.TaskViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public CustomCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.cursor_movie, parent, false);

        return new TaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {


        int idColumnIndex = mCursor.getColumnIndex(PopularMoviesContract.MovieEntry._ID);
        int nameColumnIndex = mCursor.getColumnIndex(PopularMoviesContract.MovieEntry.MOVIE_TITLE);
        int voteColumnIndex = mCursor.getColumnIndex(PopularMoviesContract.MovieEntry.MOVIE_VOTE_AVERAGE);
        int releaseColumnIndex = mCursor.getColumnIndex(PopularMoviesContract.MovieEntry.MOVIE_RELEASE_DATE);
        int movieOverview = mCursor.getColumnIndex(PopularMoviesContract.MovieEntry.MOVIE_OVERVIEW);
        int path = mCursor.getColumnIndex(PopularMoviesContract.MovieEntry.MOVIE_POSTER);
        int movieId = mCursor.getColumnIndex(PopularMoviesContract.MovieEntry.MOVIE_ID);

        mCursor.moveToPosition(position);

        final int id = mCursor.getInt(idColumnIndex);
        final String description = mCursor.getString(nameColumnIndex);
        final String priority = mCursor.getString(voteColumnIndex);
        final String imagePath = mCursor.getString(path);
        final String releaseMovie = mCursor.getString(releaseColumnIndex);
        final String overviewMovie = mCursor.getString(movieOverview);
        final String movieIdentity = mCursor.getString(movieId);


        holder.itemView.setTag(id);
        Picasso.with(mContext).load(imagePath).
                placeholder(R.drawable.load_image).
                error(R.drawable.no_image).
                into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), DetailActivity.class);

                intent.putExtra("Title", description);
                intent.putExtra("Language", "en");
                intent.putExtra("Overview", overviewMovie);
                intent.putExtra("Vote", priority);
                intent.putExtra("Release", releaseMovie);
                intent.putExtra("Path", imagePath);
                intent.putExtra("Id", movieIdentity);


                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }


    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public TaskViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ivw);

        }
    }
}