package com.hy.android.Base;

import android.app.Application;

import com.hy.android.utils.ASCrashHandler;

/**
 * Created by Administrator on 2018/4/3.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        //出现异常时，将异常保存到本地日志（/SuperSMS/errorlog.log）
        ASCrashHandler.getInstance().init(this);
        //腾讯Bugly异常上报
       // CrashReport.initCrashReport(getApplicationContext(), "210de28226", false);
    }

    public static synchronized BaseApplication getInstance() {
        return instance;
    }
}
