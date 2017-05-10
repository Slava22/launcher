package com.example.cometlauncher.launcher;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.cometlauncher.R;

public class Launcher extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences pref;
    FragmentTransaction ft;
    SharedPreferences.Editor ed;
    private ViewPager viewPager;
    private FragmentsPagerAdapterLauncher fragmentsPagerAdapter;
    private SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);

        sPref = this.getSharedPreferences("com.example.cometLauncher", Context.MODE_PRIVATE);
        ed = sPref.edit();
        ed.putBoolean("loadWelcompage", true);
        ed.apply();

        viewPager = (ViewPager) findViewById(R.id.vpagerLauncher);
        if (sPref.getBoolean("hide", true)) {
            fragmentsPagerAdapter = new FragmentsPagerAdapterLauncher(getSupportFragmentManager(), 1);
        } else {
            fragmentsPagerAdapter = new FragmentsPagerAdapterLauncher(getSupportFragmentManager(), 2);
        }
        if (viewPager != null) {
            viewPager.setAdapter(fragmentsPagerAdapter);
        }

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.registerOnSharedPreferenceChangeListener(this);

        boolean theme = sPref.getBoolean("theme", true);
        if(theme){
            setTheme(R.style.AppTheme);
        } else if (!theme){
            setTheme(R.style.MyTheme);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        recreate();
        System.out.println("Hi2!");
    }
}
