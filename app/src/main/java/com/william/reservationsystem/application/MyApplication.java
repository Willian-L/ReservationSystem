package com.william.reservationsystem.application;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class MyApplication extends Application {

    private static MyApplication app;

    public static MyApplication getInstance()
    {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        ZXingLibrary.initDisplayOpinion(this);
    }

}