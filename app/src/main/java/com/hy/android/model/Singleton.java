package com.hy.android.model;

/**
 * 单例模式  双重检测
 */
public class Singleton {

    private volatile static Singleton instance = null;

    private Singleton() {
    }

    public static Singleton getInstance() {

        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }


    /**
     *   单例模式： 私有的构造函数，私有静态成员变量。
     * ---------------------- 1、饿汉式 -----------------------------
     * public class IdGenerator {
     *      private static final IdGenerator instance = new IdGenerator();
     *      private IdGenerator() {}
     *      public static IdGenerator getInstance() {
     *      return instance;
     *      }
     * }
     * ----------------------------------------------------------------
     * ----------------------- 2、懒汉式 -------------------------------
     * public class IdGenerator {
     *       private static IdGenerator instance;
     *       private IdGenerator() { }
     *       public static synchronized IdGenerator getInstance() {
     *      if (instance == null) {
     *      instance = new IdGenerator();
     *      }
     *       return instance;
     *      }
     * }
     * -------------------------------------------------------------------
     * ----------------------- 3、双重检测 --------------------------------
     * public class IdGenerator {
     *         private volatile static IdGenerator instance;
     *         private IdGenerator() { }
     *         public static IdGenerator getInstance() {
     *             if (instance == null) {
     *                 synchronized (IdGenerator.class) {
     *                     // 此处为类级别的锁
     *                     if (instance == null) {
     *                         instance = new IdGenerator();
     *                     }
     *                 }
     *             }
     *             return instance;
     *         }
     *     }
     * --------------------------------------------------------------------
     * ----------------------- 4、静态内部类 --------------------------------
     * public class IdGenerator {
     *         private IdGenerator() { }
     *
     *         private static class SingletonHolder {
     *             private static final IdGenerator instance = new IdGenerator();
     *         }
     *
     *         public static IdGenerator getInstance() {
     *             return SingletonHolder.instance;
     *         }
     *     }
     * -------------------------------------------------------------------
     * --------------------------- 5、枚举 --------------------------------
     * public enum IdGenerator {
     *         INSTANCE;
     *         private AtomicLong id = new AtomicLong(0);
     *
     *         public long getId() {
     *             return id.incrementAndGet();
     *         }
     *     }
     * -------------------------------------------------------------------
     */


}
