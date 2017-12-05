package com.example.hp.popularmovies.Details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.popularmovies.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private List<Reviews> review;
    private Context mContext;


    public ReviewAdapter(Context context, List<Reviews> userReview) {
        this.review = userReview;
        mContext = context;
    }


    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.reviews_main;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        final ReviewHolder viewHolder = new ReviewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {

        Reviews allReviews = review.get(position);
        if (allReviews.getAuthorName().equalsIgnoreCase("")){
            holder.tv1.setText("No Reviews Found");
        }
        holder.tv1.setText(allReviews.getAuthorName());
        holder.tv2.setText(allReviews.getContent());

    }

    @Override
    public int getItemCount() {
        return review.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        TextView tv1;
        TextView tv2;


        public ReviewHolder(View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.author);
            tv2 = itemView.findViewById(R.id.content);

        }
    }

    public void settingData(List<Reviews> weatherData) {
        review = weatherData;
        notifyDataSetChanged();
    }
}
