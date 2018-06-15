package com.hy.location.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hy.location.R;
import com.hy.location.adapter.RouteAdapter;
import com.hy.location.bean.LineBean;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainPage extends AppCompatActivity {


    private RecyclerView recyclerView_line, recyclerView_line_dot;
    private TextView tv_pointName;
    private Realm mRealm;
    private RouteAdapter mRouteAdapter;
    private Context mContext;
    private List<LineBean>list_line;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        mContext=MainPage.this;
        mRealm = Realm.getDefaultInstance();
        initToolbar();
        initView();
        deleteData();
        setData();
        setRouteList();
    }

    private void initView() {
        recyclerView_line = (RecyclerView) findViewById(R.id.recyclerView_line);
        recyclerView_line_dot = (RecyclerView) findViewById(R.id.recyclerView_line_dot);
        tv_pointName= (TextView) findViewById(R.id.tv_pointName);
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
        mRealm.executeTransaction(realm -> {
            for (int i = 0; i < 10; i++) {
                LineBean bean = realm.createObject(LineBean.class);
                bean.setLongitude(109.20 + i);
                bean.setLatitude(20.01 + i);
                bean.setRouteName("线路" + i);
                bean.setPointName("路段" + i+": 起点--->终点");
                bean.setId(UUID.randomUUID().toString());
            }
        });
    }

    private void deleteData(){
        final RealmResults<LineBean> dogs=  mRealm.where(LineBean.class).findAll();

        mRealm.executeTransaction(realm -> {
            //删除第一个数据
            //dogs.deleteFirstFromRealm();
            //删除最后一个数据
           // dogs.deleteLastFromRealm();
            //删除位置为1的数据
           // dogs.deleteFromRealm(1);
            //删除所有数据
            dogs.deleteAllFromRealm();
        });
    }

    private void setRouteList(){
        list_line=mRealm.where(LineBean.class).findAll();
        mRouteAdapter = new RouteAdapter(R.layout.list_route_item,list_line);
        recyclerView_line.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView_line.setAdapter(mRouteAdapter);
        mRouteAdapter.setOnItemClickListener((adapter, view, position) -> {
            tv_pointName.setText(list_line.get(position).getPointName());
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add:
                Intent intent = new Intent(mContext, LocalActivity.class);
                startActivityForResult(intent,100);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            list_line=mRealm.where(LineBean.class).findAll();
            mRouteAdapter.notifyDataSetChanged();
        }
    }
}
