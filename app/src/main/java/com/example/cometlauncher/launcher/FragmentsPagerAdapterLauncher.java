package com.example.cometlauncher.launcher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.cometlauncher.launcher.Fragments.AllAppsFragment.AllAppsFragment;
import com.example.cometlauncher.launcher.Fragments.FavouritesFragment.FavoritesFragment;

public class FragmentsPagerAdapterLauncher extends FragmentPagerAdapter {

    int numItems;

    public FragmentsPagerAdapterLauncher(FragmentManager fm, int numItems) {
        super(fm);
        this.numItems = numItems;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AllAppsFragment.newInstance();
            case 1:
                return FavoritesFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numItems;
    }
}
