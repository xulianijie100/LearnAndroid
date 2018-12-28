package com.hy.learnlibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/4/3.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder bind;
    public Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        bind = ButterKnife.bind(this);
        initView();
        initData();
        this.mHandler = new ActivityHandler(this);
    }

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

    /**
     * 启动一个Activity
     *
     * @param activity 需要启动的Activity的Class
     */
    public void startActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    /**
     * findViewById 不需要再去强转
     */
    public <T extends View> T viewById(@IdRes int resId) {
        return (T) super.findViewById(resId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    public void handleMessage(Message msg) {
    }

    private static class ActivityHandler extends Handler {
        WeakReference<BaseActivity> mRefActivity;

        private ActivityHandler(BaseActivity activity) {
            this.mRefActivity = new WeakReference(activity);
        }

        public void handleMessage(Message msg) {
            if (this.mRefActivity != null) {
                BaseActivity activity = (BaseActivity)this.mRefActivity.get();
                if (activity != null && !activity.isFinishing()) {
                    activity.handleMessage(msg);
                }
            }
        }
    }
}
