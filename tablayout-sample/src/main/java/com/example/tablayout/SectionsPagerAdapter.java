package com.example.tablayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ambrogio.android.tablayout.TabLayoutManager;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter implements TabLayoutManager.IconPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm, Context c) {
        super(fm);
        context = c;
    }

    private Context context;

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return null;
    }


    @Override
    public int getPageTitleIconResForTabPosition(int position) {

        //not needed if getPageTitleIconDrawableForTabPosition is implemented

            /*
            //i.e. API >= 23
            switch (position) {
                case 0:
                    return  R.drawable.info;
                case 1:
                    return  R.drawable.info;
                case 2:
                    return  R.drawable.info;
            }
            */

        return 0;
    }


    @Override
    public Drawable getPageTitleIconDrawableForTabPosition(int position) {

        switch (position) {
            case 0:
                return  ContextCompat.getDrawable(context,R.drawable.selector_tab_icon_demo);
            case 1:
                return  ContextCompat.getDrawable(context,R.drawable.selector_tab_icon_demo);
            case 2:
                return ContextCompat.getDrawable(context,R.drawable.selector_tab_icon_demo);
        }

        return null;
    }
}

