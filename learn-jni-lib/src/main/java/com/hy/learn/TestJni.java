package com.hy.learn;

public class TestJni {
    /**
     *  |java reference            |java
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */

    private static final String TAG = "TestJni";

    static {
        System.loadLibrary("native-lib");
    }

    public static native String stringFromJNI();

    public native User invokeUserConstructor();

}
