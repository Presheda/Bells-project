package com.example.precious.practicalproject;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.precious.practicalproject.Fragments.Download;
import com.example.precious.practicalproject.Fragments.News;
import com.example.precious.practicalproject.Fragments.e_library;

/**
 * Created by Precious on 1/16/2018.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] { "Download", "E-library", "News"};
    private int positionOf = 3;
    public Context context;

    public FragmentAdapter(FragmentManager fm, Context context) {

        super(fm);
        this.context =  context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0){
            return new Download();
        }

        else if(position ==  1){
            return new e_library();
        }

        else {
            return new News();
        }
    }

    @Override
    public int getCount() {
        return positionOf;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }



}
