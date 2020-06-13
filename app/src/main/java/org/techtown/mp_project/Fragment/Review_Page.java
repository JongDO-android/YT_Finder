package org.techtown.mp_project.Fragment;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.techtown.mp_project.Adapter.ReviewAdapter;
import org.techtown.mp_project.Dialog.CustomDialog;
import org.techtown.mp_project.Model.Review;
import org.techtown.mp_project.Pattern.ReviewSingleton;
import org.techtown.mp_project.R;

import java.util.ArrayList;
import java.util.Locale;

public class Review_Page extends Fragment {
    private String Channel_Title;
    private String Channel_Thumbnail;
    private DatabaseReference mDataReference;
    private ReviewAdapter reviewAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Review> reviews = new ArrayList<>();

    private double grade;


    public Review_Page() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review_page, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewAdapter = new ReviewAdapter(getContext(), reviews);
        recyclerView.setAdapter(reviewAdapter);

        TextView channel_title = v.findViewById(R.id.channel_title);
        final TextView text_rating = v.findViewById(R.id.rating);
        ImageView thumbnail = v.findViewById(R.id.circleImageView2);
        final RatingBar ratingBar = v.findViewById(R.id.ratingBar);
        if(getArguments() != null){
            Channel_Title = getArguments().getString("channel_title");
            Channel_Thumbnail = getArguments().getString("thumbnail");

            channel_title.setText(Channel_Title);
            Glide.with(v.getContext()).load(Channel_Thumbnail).into(thumbnail);

            mDataReference = FirebaseDatabase.getInstance().getReference().child(Channel_Title);
            mDataReference.child("review").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            mDataReference.child("review").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    reviewAdapter.clear();
                    for(DataSnapshot reviewData : dataSnapshot.getChildren()){
                        Review review = reviewData.getValue(Review.class);

                        reviewAdapter.addReview(review);
                    }
                    grade = reviewAdapter.CalculGrade();
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ratingBar, "rating", 0.0f, (float) grade);
                    objectAnimator.setDuration(2500);
                    objectAnimator.setInterpolator(new DecelerateInterpolator());
                    objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            ratingBar.setRating((float) valueAnimator.getAnimatedValue());
                            text_rating.setText(String.format(Locale.KOREA,"%.2f", (float) valueAnimator.getAnimatedValue()));
                        }
                    });
                    objectAnimator.start();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        ImageButton write_review_btn = v.findViewById(R.id.write_review);
        write_review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(getActivity());
                customDialog.callFunction(Channel_Title);
            }
        });

        return v;
    }
}
