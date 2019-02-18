package com.hy.android.knowledge;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hy.android.R;

public class OtherActivity extends Activity {

    private static final String TAG = "OtherActivity";
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Log.e(TAG, "onCreate: -------");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: -------");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: -------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: -------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: -------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: -------");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: -------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: -------");
    }
}
