package com.hy.android.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ASCrashHandler implements Thread.UncaughtExceptionHandler {

    private static ASCrashHandler sInstance;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private SimpleDateFormat mSdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.getDefault());

    //日志文件超过50M将清理
    private long MAX_LOG_SIZE = 50 * 1024 * 1025;

    /**
     * 防止多线程中的异常导致读写不同步问题的lock
     **/
    private Lock mLock = null;
    private PackageInfo mPackageInfo;
    private boolean mPermissonDenied;


    public static ASCrashHandler getInstance() {
        if (sInstance == null) {
            sInstance = new ASCrashHandler();
        }
        return sInstance;
    }

    public void init(Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        try {
            mPackageInfo = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mLock = new ReentrantLock(true);
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mPermissonDenied = PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Log.e("ASCrashHandler", "52行-init(): " + "没有写SD卡的权限吗？ " + mPermissonDenied);
    }

    /**
     * 核心方法，当程序crash 会回调此方法， Throwable中存放这错误日志
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            Log.e("ASCrashHandler", "error=" + ex.toString() + "\n" + ex.getMessage() + "\n" + ex.getCause() + "\n" + ex.getStackTrace());
            if (!mPermissonDenied) {
                mLock.tryLock();
                String logPath;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    logPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + File.separator + "SuperSMS";

                    File file = new File(logPath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    //文件大于50M自动清理
                    logPath = logPath + File.separator + "errorlog.log";
                    File logFile = new File(logPath);
                    long logSize = getFileSize(logFile);
                    if (logSize > MAX_LOG_SIZE) {
                        logFile.delete();
                        logFile.createNewFile();
                    }

                    try {
                        FileWriter fw = new FileWriter(logFile, true);
                        fw.write("*********************************************\n");
                        // 加上当前的系统版本，机型型号 等等信息
                        if (mPackageInfo != null) {
                            fw.write("当前版本号：" + mPackageInfo.versionName + "_" + mPackageInfo.versionCode + "\n");
                        }
                        fw.write("当前系统：" + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT + "\n");
                        fw.write("制造商：" + Build.MANUFACTURER + "\n");
                        fw.write("手机型号：" + Build.MODEL + "\n");
                        fw.write("CPU架构：" + Build.CPU_ABI + "\n");
                        fw.write("错误时间：" + mSdf.format(new Date()) + "\n");
                        // 错误信息
                        StackTraceElement[] stackTrace = ex.getStackTrace();
                        fw.write("错误原因：" + ex.toString() + "\n");
                        for (int i = 0; i < stackTrace.length; i++) {
                            fw.write("----------------------------------------\n");
                            fw.write("错误文件:" + stackTrace[i].getFileName() + "\n错误类名:" + stackTrace[i].getClassName() + "\n错误方法:" + stackTrace[i]
                                    .getMethodName() + "\n错误行数:" + stackTrace[i].getLineNumber() + "\n");
                            fw.write("----------------------------------------\n");
                        }
                        fw.write("**********************************************");
                        fw.write("\n\n");
                        fw.close();
                        // 上传错误信息到服务器
                        // uploadToServer();
                    } catch (Exception e) {
                        Log.e("crash handler", "load file failed...", e.getCause());
                    }
                }
            }
            ex.printStackTrace();
            handleException(thread, ex);
        } catch (Exception e) {
            e.printStackTrace();
            //如果系统提供了默认异常处理就交给系统进行处理，否则自己进行处理。
            handleException(thread, ex);
        } finally {
            mLock.unlock();
        }
    }

    private void handleException(Thread thread, Throwable ex) {
        //如果系统提供了默认异常处理就交给系统进行处理，否则自己进行处理。
        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
            fis.close();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }
}

