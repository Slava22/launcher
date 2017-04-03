package com.example.cometlauncher.launcher;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;

import com.example.cometlauncher.ListApps;
import com.example.cometlauncher.R;

import java.util.List;


public class RecyclerView extends Activity {

    private android.support.v7.widget.RecyclerView mRecyclerView;
    private android.support.v7.widget.RecyclerView.Adapter mAdapter;
    private android.support.v7.widget.RecyclerView.LayoutManager mLayoutManager;

    private List<Application> apps;

    private int portret;
    private int landscape;
    private int nowColumns;
    private int widthScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        portret = getIntent().getIntExtra("port", 3);
        landscape = getIntent().getIntExtra("land", 5);
        ListApps la = new ListApps();
        apps = la.getList();

        getScreenOrientation();

        mRecyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(apps, heightCard(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getScreenOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            nowColumns = portret;
            mLayoutManager = new GridLayoutManager(RecyclerView.this, nowColumns);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            nowColumns = landscape;
            mLayoutManager = new GridLayoutManager(RecyclerView.this, nowColumns);
        }
    }

    public int heightCard(){
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        widthScreen = metricsB.widthPixels;
        System.out.println(metricsB.widthPixels);

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.item, null);
        return ((widthScreen / nowColumns) - view.getPaddingLeft() - view.getPaddingRight());
    }
}
