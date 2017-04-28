package com.example.cometlauncher;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class Application {
    private CharSequence appName;
    private Drawable image;
    private String packageName;
    private Intent intent;

    public Application(CharSequence name, Drawable image, String packageName, Intent intent) {
        this.appName = name;
        this.image = image;
        this.packageName = packageName;
        this.intent = intent;
    }

    public CharSequence getAppName() {
        return appName;
    }

    public Drawable getImage() {
        return image;
    }

    public String getPackageName() {
        return packageName;
    }

    public Intent getIntent() {
        return intent;
    }
}
