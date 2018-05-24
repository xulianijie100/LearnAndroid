package com.hy.location;

import android.app.Application;

import com.hy.location.bean.LocalBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public class MyApplication extends Application {

    private static List<String>localList;
    private static ArrayList<LocalBean>list=new ArrayList<>();

    public static ArrayList<LocalBean> getList() {
        return list;
    }

    public static void setList(ArrayList<LocalBean> list) {
        MyApplication.list = list;
    }

    public static List<String> getLocalList() {
        return localList;
    }

    public static void setLocalList(List<String> localList) {
        MyApplication.localList = localList;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
