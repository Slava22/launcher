package com.example.cometlauncher;

import com.example.cometlauncher.launcher.Application;

import java.util.ArrayList;
import java.util.List;


public class ListApps {
    static List<Application> apps;

    public void setApps(List<Application> app){
        apps = new ArrayList<>(app);
    }

    public List<Application> getList(){
        return apps;
    }
}
