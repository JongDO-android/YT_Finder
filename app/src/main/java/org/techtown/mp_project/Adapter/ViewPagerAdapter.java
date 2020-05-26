package org.techtown.mp_project.Adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.techtown.mp_project.Model.TabInfo;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<TabInfo> pages = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addItem(Fragment fragment, int resID){
        TabInfo tabInfo = new TabInfo(fragment, resID);
        pages.add(tabInfo);
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return pages.get(position).getFragment();
    }

    public TabInfo getTabInfo(int position){
        return pages.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

}
