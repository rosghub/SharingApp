package com.example.brandon_pc.cloudapp2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/* Viewpager adapter
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentList;

    public PagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        fragmentList = list;
    }

    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    public int getCount() {
        return fragmentList.size();
    }
}
