package com.example.hp.popularmovies.Details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hp.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {

    private List<Trailers> trailers;
    private Context mContext;
    public String videoKey;


    public TrailerAdapter(Context context, List<Trailers> movieTrailers) {
        this.trailers = movieTrailers;
        mContext = context;
    }


    @Override
    public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailer_main;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        final TrailerHolder viewHolder = new TrailerHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerHolder holder, int position) {

        Trailers allTrailers = trailers.get(position);
        videoKey = allTrailers.getVideoKey();
        String thumbnailUrl = "http://img.youtube.com/vi/" + videoKey + "/0.jpg";

        Picasso.with(mContext).load(thumbnailUrl).
                placeholder(R.drawable.load_image).
                error(R.drawable.no_image).
                into(holder.iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent youIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoKey));
                v.getContext().startActivity(youIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public class TrailerHolder extends RecyclerView.ViewHolder {

        ImageView iv;



        public TrailerHolder(View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.image_button);
        }
    }

    public void settingData(List<Trailers> weatherData) {
        trailers = weatherData;
        notifyDataSetChanged();
    }

    public String getMyString(){
        return videoKey;
    }
}