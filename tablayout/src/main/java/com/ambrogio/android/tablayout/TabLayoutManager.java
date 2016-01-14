package com.ambrogio.android.tablayout;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;


/**
 * TabLayoutManager
 * Set up a Tablayout with icons.
 */
public class TabLayoutManager {

    public interface TabIconPagerAdapter {

        /**
         * Define a Drawable for a tab icon at a given position.
         * Use a StateListDrawable for pre-lollipop devices (tinting bug in SDK &lt;= 24).
         *
         * @see <a href="http://stackoverflow.com/questions/30828951/tab-with-icon-using-tablayout-in-android-design-library">stackoverflow 1</a>
         * @see <a href="http://stackoverflow.com/questions/30872101/drawablecompat-tinting-does-not-work-on-pre-lollipop">stackoverflow 2</a>
         *
         * @param position tab position
         * @return Tab Icon Drawable
         */
        @Nullable
        Drawable getPageTitleIconDrawable(int position);


        /**
         * Define a DrawableRes for a tab icon at a given position.
         * Use a StateListDrawable for pre-lollipop devices (tinting bug in SDK &lt;= 24).
         *
         * @see <a href="http://stackoverflow.com/questions/30828951/tab-with-icon-using-tablayout-in-android-design-library">stackoverflow 1</a>
         * @see <a href="http://stackoverflow.com/questions/30872101/drawablecompat-tinting-does-not-work-on-pre-lollipop">stackoverflow 2</a>
         *
         * @param position tab position
         * @return Tab Icon Drawable Id
         */
        @DrawableRes
        int getPageTitleIconDrawableRes(int position);

    }

    private static final int version = Build.VERSION.SDK_INT;

    /**
     * TabLayout Setup.
     * @param tabLayout TabLayout
     * @param viewPager ViewPager
     */
    public static void setupWithViewPager(TabLayout tabLayout, ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        setTabsFromPagerAdapter(tabLayout, viewPager.getAdapter());
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    private static void setTabsFromPagerAdapter(TabLayout tabLayout, PagerAdapter pagerAdapter) {

        if (!(pagerAdapter instanceof TabIconPagerAdapter)) {
            tabLayout.setTabsFromPagerAdapter(pagerAdapter);
            return;
        }

        final ColorStateList tabColors = tabLayout.getTabTextColors();

        for (int i = 0; i < pagerAdapter.getCount(); i++) {

            Drawable icon = ((TabIconPagerAdapter) pagerAdapter).getPageTitleIconDrawable(i);

            if (!(icon instanceof StateListDrawable) && ( version < Build.VERSION_CODES.M)){
                throw new AssertionError("Use a StateListDrawable for SDK < 23");
            }

            if (icon == null) {
                final int iconRes = ((TabIconPagerAdapter) pagerAdapter).getPageTitleIconDrawableRes(i);
                icon = ResourcesCompat.getDrawable(tabLayout.getResources(), iconRes, null);
            }

            //use a new variable 'wrappedIcon' instead of 'icon'
            //icon = DrawableCompat.wrap(icon);
            //DrawableCompat.setTintList(icon, tabColors);

            Drawable wrappedIcon = DrawableCompat.wrap(icon);
            DrawableCompat.setTintList(wrappedIcon, tabColors);

            CharSequence tabDescription = pagerAdapter.getPageTitle(i);
            //set icon, accessibility description and titleView
            TabLayout.Tab newTab = tabLayout.newTab().setIcon(wrappedIcon).setContentDescription(tabDescription).setText(tabDescription);
            tabLayout.addTab(newTab);
        }
    }
}
