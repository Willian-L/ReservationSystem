package com.william.reservationsystem;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();
        CrashHandlerUtil.getInstance().init(this);
    }
}
