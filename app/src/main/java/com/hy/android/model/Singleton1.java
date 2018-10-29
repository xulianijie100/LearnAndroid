package com.hy.android.model;

/**
 * 懒汉式
 *
 * 懒加载，线程安全
 */

public class Singleton1 {

    private volatile static Singleton1 instance = null;

    private Singleton1() {}

    public static Singleton1 getInstance() {

        if (instance == null) {
            synchronized (Singleton1.class) {
                if (instance == null) {
                    instance = new Singleton1();
                }
            }
        }
        return instance;
    }


}
