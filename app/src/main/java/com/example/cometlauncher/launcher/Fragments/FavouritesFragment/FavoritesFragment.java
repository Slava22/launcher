package com.example.cometlauncher.launcher.Fragments.FavouritesFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.cometlauncher.Application;
import com.example.cometlauncher.ListApps;
import com.example.cometlauncher.R;
import com.example.cometlauncher.launcher.PrefActivity;

import java.util.List;

public class FavoritesFragment extends Fragment implements View.OnClickListener {

    SharedPreferences pref;
    SharedPreferences sPref;
    private android.support.v7.widget.RecyclerView mRecyclerView;
    private android.support.v7.widget.RecyclerView.Adapter mAdapter;
    private android.support.v7.widget.RecyclerView.LayoutManager mLayoutManager;
    private int portret;
    private int landscape;
    private int nowColumns;
    private int widthScreen;
    private List<Application> apps;
    private ImageButton imgBtnPref;

    public static FavoritesFragment newInstance() {
        Bundle args = new Bundle();
        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_fragment_launcher, container, false);

        imgBtnPref = (ImageButton) view.findViewById(R.id.imgBtnPref);
        imgBtnPref.setOnClickListener(this);

        ListApps la = new ListApps();
        apps = la.getFavApps();

        final EditText editText = (EditText) view.findViewById(R.id.uriText);
        editText.setOnKeyListener(new View.OnKeyListener() {
                                      public boolean onKey(View v, int keyCode, KeyEvent event) {
                                          if (event.getAction() == KeyEvent.ACTION_DOWN &&
                                                  (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                              String text = editText.getText().toString();
                                              openUri(text);
                                              return true;
                                          }
                                          return false;
                                      }
                                  }
        );

        sPref = getActivity().getSharedPreferences("com.example.cometLauncher", Context.MODE_PRIVATE);
        portret = sPref.getInt("portret", 3);
        landscape = sPref.getInt("landscape", 4);

        getScreenOrientation();

        mRecyclerView = (android.support.v7.widget.RecyclerView) view.findViewById(R.id.rvf);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewFavouritesFragmentAdapter(apps, heightCard(), getActivity().getApplicationContext(), getActivity().getPackageManager());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBtnPref:
                Intent intent = new Intent(getActivity(), PrefActivity.class);
                startActivity(intent);
        }
    }

    public void openUri(String text) {
        Intent intentUri = null;
        Uri uri = Uri.parse(text);
        if (uri.getScheme().equals("http") || uri.getScheme().equals("geo")) {
            intentUri = new Intent(Intent.ACTION_VIEW, Uri.parse(text));
        } else if (uri.getScheme().equals("tel")) {
            intentUri = new Intent(Intent.ACTION_DIAL, Uri.parse(text));
        } else if (uri.getScheme().equals("package")) {
            StringBuilder sb = new StringBuilder();
            sb.append(text);
            sb.delete(0, sb.indexOf(":") + 1);
            System.out.println(sb.toString());
            intentUri = getActivity().getPackageManager().getLaunchIntentForPackage(sb.toString());
        }
        startActivity(intentUri);
    }

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
}
