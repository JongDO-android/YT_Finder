package org.techtown.mp_project.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.techtown.mp_project.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelDetails_Page extends Fragment {


    public ChannelDetails_Page() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_channel_details_page, container, false);
    }

}
