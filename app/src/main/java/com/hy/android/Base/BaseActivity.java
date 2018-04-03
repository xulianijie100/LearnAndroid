package com.hy.android.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hy.android.utils.AppManager;

/**
 * Created by Administrator on 2018/4/3.
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 视图ID
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        AppManager.getAppManager().addActivity(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
