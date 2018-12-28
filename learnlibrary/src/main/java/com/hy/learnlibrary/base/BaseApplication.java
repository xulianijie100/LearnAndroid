package com.hy.learnlibrary.base;

import android.app.Application;

/**
 * Created by Administrator on 2018/4/3.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized BaseApplication getInstance() {
        return instance;
    }
}
