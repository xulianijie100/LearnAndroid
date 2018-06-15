package com.hy.location;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    private static Context mContext;
    public static boolean firstFlag = false;

    @Override
    public void onCreate() {
        super.onCreate();
        firstFlag = true;
        mContext = getApplicationContext();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("hy.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public static Context getContext() {
        return mContext;
    }
}
