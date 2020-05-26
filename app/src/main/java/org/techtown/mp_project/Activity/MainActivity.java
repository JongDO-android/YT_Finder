package org.techtown.mp_project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;


import org.techtown.mp_project.Adapter.ViewPagerAdapter;
import org.techtown.mp_project.Fragment.ChannelList_Page;
import org.techtown.mp_project.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        ViewPager viewPager = findViewById(R.id.viewPager);

        viewPagerAdapter.addItem(new ChannelList_Page(), R.drawable.search);
        viewPager.setAdapter(viewPagerAdapter);


    }

}
