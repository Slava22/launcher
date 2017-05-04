package com.example.cometlauncher;

import java.util.ArrayList;
import java.util.List;


public class ListApps {
    private static List<Application> apps;
    private static List<Application> favApps = new ArrayList<>();

    public static List<Application> getFavApps() {
        return favApps;
    }

    public List<Application> getApps() {
        return apps;
    }

    public void setApps(List<Application> mapps) {
        apps = mapps;
    }

    public void addToFav(Application app) {
        favApps.add(app);
    }

    public void clearFavourites() {
        favApps.clear();
    }
}