package com.hy.location.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.hy.location.R;
import com.hy.location.adapter.PointAdapter;
import com.hy.location.adapter.RouteAdapter;
import com.hy.location.bean.LineBean;
import com.hy.location.utils.AMapUtil;
import com.hy.location.utils.LoadDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainPage extends AppCompatActivity implements View.OnClickListener, RouteSearch.OnRouteSearchListener {


    private static final int MY_PERMISSION_REQUEST_CODE = 1100;
    private RecyclerView recyclerView_line, recyclerView_line_dot;
    private TextView tv_pointName;
    private Realm mRealm;
    private RouteAdapter mRouteAdapter;
    private Context mContext;
    private List<LineBean> list_line;
    private PointAdapter mPointAdapter;

    private RouteSearch mRouteSearch;
    private DrivePath mDrivePath;

    protected LatLonPoint mEndLatlng;
    protected LatLonPoint mStartLatlng;
    private DriveRouteResult mDriveRouteResult;
    private LineBean currLineBean;
    private LoadDialog ld;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        mContext = MainPage.this;
        mRealm = Realm.getDefaultInstance();
        initToolbar();
        initView();
        deleteData();
        setData();
        setRouteList();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_SETTINGS
                },
                MY_PERMISSION_REQUEST_CODE
        );
    }

    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了,
                searchLocal();

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails();
            }
        }
    }

    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("获取位置信息需开启位置权限”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void initView() {
        ld=new LoadDialog(mContext);
        recyclerView_line = (RecyclerView) findViewById(R.id.recyclerView_line);
        recyclerView_line_dot = (RecyclerView) findViewById(R.id.recyclerView_line_dot);
        tv_pointName = (TextView) findViewById(R.id.tv_pointName);
        tv_pointName.setOnClickListener(this);
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

    private void searchLocal() {
        ld.show();
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        //经度--纬度: 113.372864---22.978249
        mStartLatlng = new LatLonPoint(currLineBean.getLatitude(), currLineBean.getLongitude());
        mEndLatlng = new LatLonPoint(Double.valueOf("24"), Double.valueOf("120"));
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartLatlng, mEndLatlng);
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DRIVING_SINGLE_DEFAULT, null,
                null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
        mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
    }

    private void setData() {
        mRealm.executeTransaction(realm -> {
            for (int i = 0; i < 10; i++) {
                LineBean bean = realm.createObject(LineBean.class);
                bean.setLongitude(109.20 + i);
                bean.setLatitude(20.01 + i);
                bean.setRouteName("线路" + i);
                bean.setPointName("路段" + i + ": 起点--->终点");
                bean.setId(UUID.randomUUID().toString());
            }
        });
    }

    private void deleteData() {
        final RealmResults<LineBean> dogs = mRealm.where(LineBean.class).findAll();

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

    private void setRouteList() {
        list_line = mRealm.where(LineBean.class).findAll();
        mRouteAdapter = new RouteAdapter(R.layout.list_route_item, list_line);
        recyclerView_line.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView_line.setAdapter(mRouteAdapter);
        mRouteAdapter.setOnItemClickListener((adapter, view, position) -> {
            currLineBean = list_line.get(position);
            tv_pointName.setEnabled(true);
            tv_pointName.setText(list_line.get(position).getPointName());
        });

        mPointAdapter = new PointAdapter(mContext, null);
        recyclerView_line_dot.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView_line_dot.setAdapter(mPointAdapter);
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
                startActivityForResult(intent, 100);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            list_line = mRealm.where(LineBean.class).findAll();
            mRouteAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int errorCode) {
        if(ld.isShowing()){
            ld.cancel();
        }
        if (errorCode == 1000) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    mDriveRouteResult = driveRouteResult;
                    mDrivePath = mDriveRouteResult.getPaths().get(0);
                    String dur = AMapUtil.getFriendlyTime((int) mDrivePath.getDuration());
                    String dis = AMapUtil.getFriendlyLength((int) mDrivePath.getDistance());
                    String time = dur + "(" + dis + ")";
                    mPointAdapter.add(mDrivePath.getSteps());
                } else if (driveRouteResult != null && driveRouteResult.getPaths() == null) {
                    Toast.makeText(mContext, R.string.no_result, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(mContext, R.string.no_result, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, errorCode, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pointName:
                boolean granted = checkPermissionAllGranted(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE
                });

                if (granted) {
                    searchLocal();
                } else {
                    requestPermission();
                }

                break;
        }
    }
}
