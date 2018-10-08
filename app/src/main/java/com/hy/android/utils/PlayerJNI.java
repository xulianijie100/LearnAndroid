package com.hy.android.utils;

import android.util.Log;

public class PlayerJNI {
    private static final String TAG = "PlayerJNI";
    static {
        System.loadLibrary("native-lib");
    }


    public void getData(int a,int b){
        Log.e(TAG, a+"---"+b);
        //打印值为 PlayerJNI: 111---666
    }

    public static native String stringFromJNI();

    public static native void funFromJava(Object obj,String id);
}
