package com.hy.android.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import android.view.View;
import com.hy.android.Component.ApplicationComponent;
import com.hy.android.base.BaseActivity;
import com.hy.android.R;
import com.hy.android.model.BikeStrategy;
import com.hy.android.model.Observable;
import com.hy.android.model.Observer;
import com.hy.android.model.Travel;
import com.hy.android.model.WalkStrategly;
import com.hy.android.model.Weather;

public class TestFunActivity  extends BaseActivity{

    private static final String TAG = "TestFunActivity";

    @Override
    public int getContentLayout() {
        return R.layout.activity_view;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        initToolbar();
    }

    @Override
    public void initData() {

        //---------------------观察者模式-------------------------------

        Observer<Weather>observer1=new Observer<Weather>(){
            @Override
            public void onUpdate(Observable<Weather> observable, Weather data) {
                Log.e(TAG,data.toString());
            }
        };
        Observer<Weather>observer2=new Observer<Weather>(){
            @Override
            public void onUpdate(Observable<Weather> observable, Weather data) {
                Log.e(TAG,data.toString());
            }
        };

        Observable<Weather>observable=new Observable<>();
        observable.register(observer1);
        observable.register(observer2);

        Weather weather=new Weather("晴转多云...");
        observable.notifyObservers(weather);


        //---------------------策略模式-------------------------------

        Travel travel=new Travel();

        WalkStrategly walkStrategly=new WalkStrategly();
        travel.setStrategy(walkStrategly);
        travel.travel();

        BikeStrategy bikeStrategy=new BikeStrategy();
        travel.setStrategy(bikeStrategy);
        travel.travel();

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("View");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRetry() {

    }
}
