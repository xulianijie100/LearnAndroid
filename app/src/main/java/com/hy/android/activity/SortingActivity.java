package com.hy.android.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import com.hy.android.Base.BaseActivity;
import com.hy.android.R;

public class SortingActivity extends BaseActivity {
    @Override
    public int bindLayout() {
        return R.layout.activity_rx;
    }

    @Override
    public void initView() {
        initToolbar();
    }

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
    protected void initData() {

    }
}
