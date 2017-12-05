package com.example.hp.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hp.popularmovies.Details.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterHolder>{



    private List<Movies> movies;
    private Context mContext;


    public MoviesAdapter(Context context, List<Movies> movies){
        this.movies = movies;
        mContext = context;
    }




    @Override
    public MoviesAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.display;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        final MoviesAdapterHolder viewHolder = new MoviesAdapterHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesAdapterHolder holder, final int position) {

        final Movies moviesAll = movies.get(position);
        String location = moviesAll.getPath();

        Picasso.with(mContext).load(location).
                placeholder(R.drawable.load_image).
                error(R.drawable.no_image).
                into(holder.iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), DetailActivity.class);

                intent.putExtra("Title", moviesAll.getTitle());
                intent.putExtra("Language", moviesAll.getLanguage());
                intent.putExtra("Overview", moviesAll.getOverview());
                intent.putExtra("Vote", moviesAll.getVote());
                intent.putExtra("Release", moviesAll.getRelease());
                intent.putExtra("Path", moviesAll.getPath());
                intent.putExtra("Id", moviesAll.getMovieId());


                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MoviesAdapterHolder extends RecyclerView.ViewHolder{

        ImageView iv;
        public MoviesAdapterHolder(View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.iv);

        }

    }

    public void setWeatherData(List<Movies> weatherData) {
        movies = weatherData;
        notifyDataSetChanged();
    }


}

