package com.hy.android.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hy.android.R;
import com.hy.android.base.BaseActivity;

public class SortingActivity extends BaseActivity {


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("排序");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_rx;
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        initToolbar();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onRetry() {

    }
}
