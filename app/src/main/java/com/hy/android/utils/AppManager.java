
package com.hy.android.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.ConcurrentModificationException;
import java.util.Stack;

public class AppManager {
    private static Stack<Activity> activityStack;
    private static volatile AppManager instance;

    private AppManager() {

    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }

    public int getActivitySize() {
        if (activityStack == null)
            return -1;
        else
            return activityStack.size();
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack == null) {
            return;
        }
        //避免抛出 ConcurrentModificationException
        try {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    activity.finish();
                    activityStack.remove(activity);
                    activity = null;
                    return;
                }
            }

        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {

        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     *
     * @param context      上下文
     * @param isBackground 是否开开启后台运行
     *                     <p/>
     *                     在android2.2版本之后则不能再使用restartPackage()方法，而应该使用killBackgroundProcesses()方法
     */
    public void AppExit(Context context, Boolean isBackground) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
        } catch (Exception e) {

        } finally {
            // 注意，如果您有后台程序运行，请不要支持此句子

        }
    }

    /**
     * 重启app,在activity里执行
     *
     * @param context
     */
    public void ReStartApp(Context context) {
        Activity activity = currentActivity();
        activityStack.remove(activity);
        AppExit(context, true);
        Intent i = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
        activity.finish();
        activity = null;

    }

    /**
     * 获取指定Activity
     *
     * @return
     */
    public Activity getActivity(Class<?> cls) {
        Activity mActivity = null;
        if (activityStack == null) {
            return null;
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                mActivity = activity;
                break;
            }
        }
        return mActivity;
    }

}
