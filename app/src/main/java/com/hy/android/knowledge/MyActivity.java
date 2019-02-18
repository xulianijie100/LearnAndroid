package com.hy.android.knowledge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hy.android.R;

public class MyActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MyActivity";
    private TextView tv_next;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Log.e(TAG, "onCreate: -------");
        initView();
    }

    private void initView() {
        tv_next = findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_next:
                startActivity(new Intent(MyActivity.this,OtherActivity.class));
                break;
            default:
                break;
        }
    }
}
