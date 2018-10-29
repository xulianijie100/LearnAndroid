package com.hy.android.model;

/**
 * 饿汉式
 */

public class Singleton2 {

    private volatile static Singleton2 instance = new Singleton2();

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        return instance;
    }


}
