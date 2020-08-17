package com.example.myapplication;

import android.app.Application;
import android.content.res.Resources;

public class MyApplication extends Application {

    private Resources resources;
    @Override
    public void onCreate() {
        super.onCreate();

        try {
            HookUtil.hookAMS();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return resources;
    }
}
