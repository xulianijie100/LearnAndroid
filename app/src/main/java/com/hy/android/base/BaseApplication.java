package com.hy.android.base;

import android.app.Application;

import com.hy.android.Component.ApplicationComponent;
import com.hy.android.Component.DaggerApplicationComponent;
import com.hy.android.module.ApplicationModule;
import com.hy.android.module.HttpModule;
import com.hy.android.utils.ASCrashHandler;

/**
 * Created by Administrator on 2018/4/3.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;
    private ApplicationComponent mApplicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();

        //出现异常时，将异常保存到本地日志（/HyLog/errorlog.log）
        ASCrashHandler.getInstance().init(this);
        //腾讯Bugly异常上报
       // CrashReport.initCrashReport(getApplicationContext(), "210de28226", false);
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
