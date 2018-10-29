package com.hy.android.model;

/**
 * Holder模式
 */

public class Singleton3 {

    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static Singleton3 instance = new Singleton3();
    }

    private Singleton3() {}

    public static Singleton3 getInstance() {
        return SingletonHolder.instance;
    }
}
