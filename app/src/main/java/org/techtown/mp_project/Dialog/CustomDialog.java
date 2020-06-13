package org.techtown.mp_project.Dialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.techtown.mp_project.Model.Review;
import org.techtown.mp_project.R;

import java.util.ArrayList;

public class CustomDialog {
    private Context context;
    private DatabaseReference mRef;
    private Float rating;

    public CustomDialog(Context context){
        this.context = context;
    }

    public void callFunction(final String channel_title){
        final Dialog dialog = new Dialog(context);

        mRef = FirebaseDatabase.getInstance().getReference();

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.review_dialog);
        dialog.show();

        final EditText nickname = (EditText) dialog.findViewById(R.id.editText_nickname);
        final EditText review = (EditText) dialog.findViewById(R.id.editText_review);
        final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = v;
                ratingBar.setRating(v);
            }
        });

        final Button ok_btn = (Button) dialog.findViewById(R.id.OK_btn);
        final Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(nickname.getText())){
                    Toast.makeText(context, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(review.getText())){
                    Toast.makeText(context, "리뷰를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(rating == null) {
                    Toast.makeText(context, "별점을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    Review review_obj = new Review();
                    review_obj.setNickname(nickname.getText().toString());
                    review_obj.setReview(review.getText().toString());
                    review_obj.setRating(rating);

                    mRef.child(channel_title).child("review").push().setValue(review_obj);
                    dialog.dismiss();
                }
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "등록 취소", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
