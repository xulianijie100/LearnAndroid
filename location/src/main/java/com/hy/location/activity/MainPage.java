package com.hy.location.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.hy.location.R;
import com.hy.location.adapter.LocalAdapter;
import com.hy.location.adapter.RouteAdapter;
import com.hy.location.bean.LineBean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;

public class MainPage extends AppCompatActivity {


    private RecyclerView recyclerView_line, recyclerView_line_dot;
    private Realm mRealm;
    private RouteAdapter mRouteAdapter;
    private Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        initToolbar();
        initView();
        setData();
        setRouteList();
    }

    private void initView() {
        mContext=MainPage.this;
        recyclerView_line = (RecyclerView) findViewById(R.id.recyclerView_line);
        recyclerView_line_dot = (RecyclerView) findViewById(R.id.recyclerView_line_dot);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.app_name);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }


    private void setData() {
        mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(realm -> {
            for (int i = 0; i < 10; i++) {
                LineBean bean = realm.createObject(LineBean.class);
                bean.setLongitude(109.20 + i);
                bean.setLatitude(20.01 + i);
                bean.setPointName("线路" + i);
                bean.setId(UUID.randomUUID().toString());
            }
        });
    }

    private void setRouteList(){
        List<LineBean>list_line=mRealm.where(LineBean.class).findAll();
        mRouteAdapter = new RouteAdapter(R.layout.list_route_item,list_line);
        recyclerView_line.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView_line.setAdapter(mRouteAdapter);
    }
}
