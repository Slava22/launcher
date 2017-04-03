package com.example.cometlauncher.welcompage;

import com.example.cometlauncher.R;

public enum ResourcesModel {
    FIRST_SCREEN(R.layout.first),
    SECOND_SCREEN(R.layout.second),
    THIRD_SCREEN(R.layout.third),
    FOURTH_SCREEN(R.layout.fourth);

    private int mLayoutResourceId;

    ResourcesModel(int layoutResId) {
        mLayoutResourceId = layoutResId;
    }

    public int getLayoutResourceId() {
        return mLayoutResourceId;
    }
}
