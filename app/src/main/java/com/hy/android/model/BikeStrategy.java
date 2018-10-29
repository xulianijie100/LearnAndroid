package com.hy.android.model;

import android.util.Log;

public class BikeStrategy implements Strategy {
    @Override
    public void travel() {
        Log.e("Strategy==","bike");
    }
}
