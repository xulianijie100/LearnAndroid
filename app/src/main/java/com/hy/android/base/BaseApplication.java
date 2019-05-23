package com.hy.android.base;

import com.hy.android.utils.ASCrashHandler;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

public class BaseApplication extends LitePalApplication {
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //出现异常时，将异常保存到本地日志（/HyLog/errorlog.log）
        ASCrashHandler.getInstance().init(this);
        //腾讯Bugly异常上报
        // CrashReport.initCrashReport(getApplicationContext(), "210de28226", false);
        LitePal.initialize(this);
    }

    public static BaseApplication getInstance() {
        return instance;
    }

}
