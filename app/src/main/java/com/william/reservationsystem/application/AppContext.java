package com.william.reservationsystem.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.william.reservationsystem.controller.LoginUtil;

import java.util.LinkedList;
import java.util.List;

public class AppContext extends Application {

    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = getApplicationContext();
    }

    public static Context getContext()
    {
        return instance;
    }
}
