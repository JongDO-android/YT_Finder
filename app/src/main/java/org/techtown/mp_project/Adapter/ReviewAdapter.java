package org.techtown.mp_project.Adapter;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.mp_project.Model.Review;
import org.techtown.mp_project.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private Context context;
    private ArrayList<Review> reviews;

    public ReviewAdapter(Context context, ArrayList<Review> reviews){
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(v);
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{
        private TextView nickname;
        private TextView review;
        private RatingBar ratingBar;

        private ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            nickname = itemView.findViewById(R.id.nickname);
            review = itemView.findViewById(R.id.review);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        TextView nickname = holder.nickname;
        TextView review = holder.review;
        RatingBar ratingBar = holder.ratingBar;

        Review rv = reviews.get(position);

        nickname.setText(rv.getNickname());
        review.setText(rv.getReview());

        ratingBar.setRating(rv.getRating());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

}
