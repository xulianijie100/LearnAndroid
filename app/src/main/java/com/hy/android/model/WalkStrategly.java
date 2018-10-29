package com.hy.android.model;


import android.util.Log;

public class WalkStrategly implements Strategy{
    @Override
    public void travel() {
        Log.e("Strategy==","walk");
    }
}
