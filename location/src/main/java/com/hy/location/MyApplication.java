package com.hy.location;

import android.app.Application;

/**
 * Created by Administrator on 2018/5/24.
 */

public class MyApplication extends Application {

    public static boolean firstFlag=false;
    @Override
    public void onCreate() {
        super.onCreate();
        firstFlag=true;
    }
}
