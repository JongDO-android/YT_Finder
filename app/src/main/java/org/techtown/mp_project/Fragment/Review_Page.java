package org.techtown.mp_project.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import org.techtown.mp_project.Dialog.CustomDialog;
import org.techtown.mp_project.R;

public class Review_Page extends Fragment {

    public Review_Page() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review_page, container, false);
        ImageButton write_review_btn = v.findViewById(R.id.write_review);
        write_review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(getActivity());
                customDialog.callFunction();
            }
        });

        return v;
    }

}
