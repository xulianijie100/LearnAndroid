package com.hy.learn;

public class TestJni {

    private static final String TAG =TestJni.class.getSimpleName();

    static {
        System.loadLibrary("native-lib");
    }

    public static native String stringFromJNI();

    //jni访问java类字段
    public static native void accessField(UserBean userBean);

    //jni访问java类静态字段
    public static native void accessStaticField(UserBean userBean);

    //jni调用java类方法
    public static native void callMethod(UserBean userBean);

    //jni调用java类静态方法
    public static native void callStaticMethod(UserBean userBean);

    //jni通过接口参数回调java类方法
    public static native void callbackMethod(ICallback callback);

    //jni子线程通过接口参数回调java类方法
    public static native void callbackChildThread(ICallback callback);

    //jni访问java类构造器返回对象
    public static native UserBean callConstructor();

}
