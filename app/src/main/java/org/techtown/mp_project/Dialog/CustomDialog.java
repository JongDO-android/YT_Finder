package org.techtown.mp_project.Dialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.mp_project.R;

public class CustomDialog {
    private Context context;

    public CustomDialog(Context context){
        this.context = context;
    }

    public void callFunction(){
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.review_dialog);
        dialog.show();

        final EditText nickname = (EditText) dialog.findViewById(R.id.editText_nickname);
        final EditText review = (EditText) dialog.findViewById(R.id.editText_review);
        final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);

        final Button ok_btn = (Button) dialog.findViewById(R.id.OK_btn);
        final Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "리뷰 등록!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "등록 취소!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
