package com.hy.android.utils;

import android.util.Log;

public class PlayerJNI {
    private static final String TAG = "PlayerJNI";

    public static final int KEY_WIDTH=0x1001;
    public static final int KEY_HEIGHT=0x1002;
    public static final int KEY_BIT_RATE=0x2001;
    public static final int KEY_SAMPLE_RATE=0x2002;
    public static final int KEY_AUDIO_FORMAT=0x2003;
    public static final int KEY_CHANNEL_COUNT=0x2004;
    public static final int KEY_FRAME_SIZE=0x2005;

    public static final int EOF=-541478725;

    static {
        System.loadLibrary("native-lib");
    }

    public void getData(int a,int b){
        Log.e(TAG, a+"---"+b);
        //打印值为 PlayerJNI: 111---666
    }

    public static native String stringFromJNI();

    public static native void funFromJava(Object obj,String id);

    public static native int start();
    public static native int input(byte[] data);
    public static native int output(byte[] data);
    public static native int get(int key);

}
