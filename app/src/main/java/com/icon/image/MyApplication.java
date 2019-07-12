package com.icon.image;

import android.app.Application;

import org.litepal.LitePalApplication;


/**
 * Created by coder on 2016/6/28.
 */
public class MyApplication extends LitePalApplication {

    private static MyApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();

    }


    public static MyApplication getIntstance() {
        return mApplication;
    }
}
