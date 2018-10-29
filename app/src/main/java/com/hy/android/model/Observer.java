package com.hy.android.model;

/**
 * 观察者
 * @param <T>
 */
public interface Observer<T> {

    void onUpdate(Observable<T> observable,T data);
}
