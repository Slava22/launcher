package com.example.cometlauncher.launcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;

import com.example.cometlauncher.ListApps;
import com.example.cometlauncher.R;

import cn.zhaiyifan.rememberedittext.RememberEditText;

public class PrefActivity extends PreferenceActivity {

    SharedPreferences sPref;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
        sPref = this.getSharedPreferences("com.example.cometLauncher", Context.MODE_PRIVATE);
        ed = sPref.edit();

        Preference clearFav = findPreference("clear_favourites");
        clearFav.setOnPreferenceClickListener((new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ListApps la = new ListApps();
                la.clearFavourites();
                return true;
            }
        }));

        Preference clearUri = findPreference("clear_uri");
        clearUri.setOnPreferenceClickListener((new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                RememberEditText.clearCache(getApplicationContext());
                return true;
            }
        }));

        Preference hide = findPreference("favourites");

        hide.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean checked = ((SwitchPreference) preference)
                        .isChecked();
                if (checked) {
                    ed = sPref.edit();
                    ed.putBoolean("hide", true);
                    ed.apply();
                } else {
                    ed = sPref.edit();
                    ed.putBoolean("hide", false);
                    ed.apply();
                }
                return true;
            }

        });
    }
}