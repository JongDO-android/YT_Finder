package org.techtown.mp_project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import org.techtown.mp_project.Adapter.ViewPagerAdapter;
import org.techtown.mp_project.Fragment.ChannelDetails_Page;
import org.techtown.mp_project.Fragment.ChannelPlaylist_page;
import org.techtown.mp_project.Fragment.Review_Page;
import org.techtown.mp_project.Model.TabInfo;
import org.techtown.mp_project.R;

public class ChannelInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_info);
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPagerAdapter.addItem(new ChannelPlaylist_page(), R.drawable.playlist);
        viewPagerAdapter.addItem(new Review_Page(), R.drawable.review);
        viewPagerAdapter.addItem(new ChannelDetails_Page(), R.drawable.information);

        for(int i = 0 ; i < viewPagerAdapter.getCount() ; i ++){
            tabLayout.getTabAt(i).setIcon(viewPagerAdapter.getTabInfo(i).getIconRes());
        }
        ChannelPlaylist_page channelPlaylist_page = (ChannelPlaylist_page) viewPagerAdapter.getItem(0);
        Intent intent = getIntent();
        Bundle bundle = new Bundle(3);
        bundle.putString("channelID", intent.getStringExtra("channelID"));
        bundle.putString("channel_title", intent.getStringExtra("channel_title"));
        bundle.putString("thumbnail", intent.getStringExtra("thumbnail"));

        channelPlaylist_page.setArguments(bundle);

    }
}
