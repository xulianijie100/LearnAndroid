package com.hy.android.model;

public interface Observer<T> {

    void onUpdate(Observable<T> observable,T data);

}
