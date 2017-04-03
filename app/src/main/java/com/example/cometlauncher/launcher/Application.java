package com.example.cometlauncher.launcher;


public class Application {
    String name;
    int image;
    int count;

    public Application(String name, int image){
        this.name = name;
        this.image = image;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
