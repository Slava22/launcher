package com.example.cometlauncher.welcompage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.cometlauncher.welcompage.fragments.FirstFragment;
import com.example.cometlauncher.welcompage.fragments.FourthFragment;
import com.example.cometlauncher.welcompage.fragments.SecondFragment;
import com.example.cometlauncher.welcompage.fragments.ThirdFragment;

public class FragmentsPagerAdapterWelcompage extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 4;

    public FragmentsPagerAdapterWelcompage(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FirstFragment.newInstance();
            case 1:
                return SecondFragment.newInstance();
            case 2:
                return ThirdFragment.newInstance();
            case 3:
                return FourthFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

}