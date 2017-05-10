package com.example.cometlauncher.launcher.Fragments.AllAppsFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cometlauncher.Application;
import com.example.cometlauncher.ListApps;
import com.example.cometlauncher.R;

import java.util.List;

import static android.content.ContentValues.TAG;

public class AllAppsFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences pref;
    SharedPreferences sPref;
    private android.support.v7.widget.RecyclerView mRecyclerView;
    private android.support.v7.widget.RecyclerView.Adapter mAdapter;
    private android.support.v7.widget.RecyclerView.LayoutManager mLayoutManager;
    private int portret;
    private int landscape;
    private int nowColumns;
    private boolean theme;
    private boolean favourites;
    private int widthScreen;
    private List<Application> apps;

    public static AllAppsFragment newInstance() {
        Bundle args = new Bundle();
        AllAppsFragment fragment = new AllAppsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        pref.registerOnSharedPreferenceChangeListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_apps_fragment_launcher, container, false);

        ListApps la = new ListApps();
        apps = la.getApps();

        sPref = getActivity().getSharedPreferences("com.example.cometLauncher", Context.MODE_PRIVATE);
        portret = sPref.getInt("portret", 3);
        landscape = sPref.getInt("landscape", 4);

        getScreenOrientation();

        mRecyclerView = (android.support.v7.widget.RecyclerView) view.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAllAppsFragmentAdapter(apps, heightCard(), getContext(), getActivity().getPackageManager());
        mRecyclerView.setAdapter(mAdapter);


        IntentFilter iFilter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        getActivity().registerReceiver(myReceiver, iFilter);


        return view;
    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
                Log.d(TAG,"Del");
            }
        }
    };

    private void getScreenOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            nowColumns = portret;
            mLayoutManager = new GridLayoutManager(getActivity(), nowColumns);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            nowColumns = landscape;
            mLayoutManager = new GridLayoutManager(getActivity(), nowColumns);
        }
    }

    public int heightCard() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        widthScreen = metricsB.widthPixels;
        System.out.println(metricsB.widthPixels);

        LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.cardview, null);
        return ((widthScreen / nowColumns) - view.getPaddingLeft() - view.getPaddingRight());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (pref.getString("count_columns", "").equals("46")) {
            portret = 4;
            landscape = 6;
        } else if (pref.getString("count_columns", "").equals("57")) {
            portret = 5;
            landscape = 7;
        }

        if (pref.getString("theme", "").equals("1")){
            theme = true;
        } else if (pref.getString("theme", "").equals("-1")){
            theme = false;
        }

        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("portret", portret);
        ed.putInt("landscape", landscape);
        ed.putBoolean("theme", theme);
        ed.apply();
    }
}
