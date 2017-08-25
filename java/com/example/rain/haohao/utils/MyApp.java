package com.example.rain.haohao.utils;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;

import org.xutils.x;



public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PlatformConfig.setQQZone("1106036236", "mjFCi0oxXZKZEWJs");
        x.Ext.init(this);
    }
}
