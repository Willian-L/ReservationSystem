package com.william.reservationsystem.controller.LoginAndRegister;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

public class MyAplication extends Application {

    private static MyAplication mInstance;
    private static List<Activity> activityList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyAplication getInstance(){
        return mInstance;
    }

    public void addActivity(Activity activity){
        activityList.add(activity);
    }

    public void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public void exitAllActivity(){
        for(Activity activity:activityList){
            activity.finish();
        }
    }
}
