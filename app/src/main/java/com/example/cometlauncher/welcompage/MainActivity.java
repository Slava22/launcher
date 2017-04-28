package com.example.cometlauncher.welcompage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.cometlauncher.*;
import com.example.cometlauncher.Application;
import com.example.cometlauncher.launcher.Launcher;
import com.example.cometlauncher.launcher.PrefActivity;
import com.viewpagerindicator.CirclePageIndicator;
import com.yandex.metrica.YandexMetrica;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnNext;
    private ViewPager viewPager;
    private FragmentsPagerAdapterWelcompage fragmentsPagerAdapter;
    private boolean isChecked;

    List<Application> apps;
    List<ApplicationInfo> appsInfo;

    private SharedPreferences sPref;
    private boolean loadWelcompage;

    private String API_key = "0fab8b33-c3e4-423a-b6c9-6a96643cdb7c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcompage);

        // Инициализация AppMetrica SDK
        YandexMetrica.activate(getApplicationContext(), API_key);
        // Отслеживание активности пользователей
        YandexMetrica.enableActivityAutoTracking(getApplication());

        sPref = this.getSharedPreferences("com.example.cometLauncher", Context.MODE_PRIVATE);
        loadWelcompage = sPref.getBoolean("loadWelcompage", false);
        if (loadWelcompage == true) {
            Intent intent = new Intent(MainActivity.this, Launcher.class);
            startActivity(intent);
            this.finish();
        }

        initializationData();
        ListApps la = new ListApps();
        la.setApps(apps);

        btnNext = (Button) findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.vpager);
        fragmentsPagerAdapter = new FragmentsPagerAdapterWelcompage(getSupportFragmentManager());

        if (viewPager != null) {
            viewPager.setAdapter(fragmentsPagerAdapter);
        }
        CirclePageIndicator circleIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);
    }

    public void initializationData() {
        PackageManager packageManager = getPackageManager();
        appsInfo = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        apps = new ArrayList<>();
        for (int i = 0; i < appsInfo.size(); i++) {
            Intent intent = packageManager.getLaunchIntentForPackage(appsInfo.get(i).packageName);
            if (intent != null) {
                apps.add(new Application(appsInfo.get(i).loadLabel(packageManager),
                        appsInfo.get(i).loadIcon(packageManager), appsInfo.get(i).packageName, packageManager.getLaunchIntentForPackage(appsInfo.get(i).packageName)));
            }
            System.out.println(appsInfo.get(i).packageName);
        }
        Drawable bd = this.getResources().getDrawable(R.drawable.ic_pref);
        apps.add(new Application("Preference", bd, null, new Intent(getApplicationContext(), PrefActivity.class)));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonNext:
                SharedPreferences.Editor ed = sPref.edit();
                Intent intent = new Intent(this, Launcher.class);
                if (viewPager.getCurrentItem() < fragmentsPagerAdapter.getCount() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else if (viewPager.getCurrentItem() + 1 == fragmentsPagerAdapter.getCount()) {
                    isChecked = ((RadioButton) findViewById(R.id.radioButton46)).isChecked();
                    if (isChecked) {
                        ed.putInt("portret", 4);
                        ed.putInt("landscape", 6);
                    } else {
                        ed.putInt("portret", 5);
                        ed.putInt("landscape", 7);
                    }
                    ed.apply();
                    startActivity(intent);
                    this.finish();
                }
        }
    }

}

/*
if (mNightMode){
setTheme(R.style.AppThemeDark_NoActionBar);
} else{
setTheme(R.style.AppTheme_NoActionBar);
}
 */