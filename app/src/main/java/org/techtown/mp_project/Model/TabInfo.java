package org.techtown.mp_project.Model;

import androidx.fragment.app.Fragment;

public class TabInfo {
    private int IconRes;
    private Fragment fragment;

    public TabInfo(Fragment fragment, int ResID){
        this.fragment = fragment;
        this.IconRes = ResID;
    }

    public int getIconRes(){
        return IconRes;
    }
    public Fragment getFragment(){
        return fragment;
    }

}
