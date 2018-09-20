package com.hy.android.utils;

public class PlayerJNI {

    static {
        System.loadLibrary("native-lib");
    }


    public static native String stringFromJNI();
}
