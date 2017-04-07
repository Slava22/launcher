package com.example.cometlauncher.welcompage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.cometlauncher.*;
import com.example.cometlauncher.launcher.Application;
import com.example.cometlauncher.launcher.RecyclerView;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnNext;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private FragmentsPagerAdapter fragmentsPagerAdapter;
    private boolean isChecked;

    List<Application> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializationData();
        ListApps la = new ListApps();
        la.setApps(apps);

        btnNext = (Button) findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.vpager);
        fragmentsPagerAdapter = new FragmentsPagerAdapter(getSupportFragmentManager());
        //viewPagerAdapter = new ViewPagerAdapter(this);
        if (viewPager != null) {
            viewPager.setAdapter(fragmentsPagerAdapter);
        }
        CirclePageIndicator circleIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);
    }

    public void initializationData() {
        apps = new ArrayList<>();
        Random rnd = new Random();

        List<Integer> icons = new ArrayList<>();
        icons = init();
        for (int i = 0; i < 10000; i++){
            int a = rnd.nextInt(icons.size());
            apps.add(new Application(new String("App " + Integer.toString(i + 1, 16)), icons.get(a)));
            icons.remove(a);
            if(icons.size() == 1){
                icons = init();
            }
        }
    }

    public List<Integer> init(){
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.ic_alien);
        list.add(R.drawable.ic_binoculars);
        list.add(R.drawable.ic_camcorder_pro);
        list.add(R.drawable.ic_fantasy);
        list.add(R.drawable.ic_musical);
        list.add(R.drawable.ic_gallery);
        list.add(R.drawable.ic_comet);
        list.add(R.drawable.ic_screen_rotation_black_24dp);
        list.add(R.drawable.ic_system_update_black_24dp);
        return list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonNext:
                Intent intent = new Intent(MainActivity.this, RecyclerView.class);
                if (viewPager.getCurrentItem() < fragmentsPagerAdapter.getCount() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else if (viewPager.getCurrentItem() + 1 == fragmentsPagerAdapter.getCount()) {
                    isChecked = ((RadioButton) findViewById(R.id.radioButton46)).isChecked();
                    if (isChecked) {
                        intent.putExtra("port", 4);
                        intent.putExtra("land", 6);
                    } else {
                        intent.putExtra("port", 5);
                        intent.putExtra("land", 7);
                    }
                    startActivity(intent);
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