package com.hy.location.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.text.TextUtils;
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
import com.amap.api.services.route.RouteSearchCity;
import com.amap.api.services.route.WalkRouteResult;
import com.hy.location.R;
import com.hy.location.adapter.PointAdapter;
import com.hy.location.adapter.RouteAdapter;
import com.hy.location.bean.LineBean;
import com.hy.location.bean.PointBean;
import com.hy.location.utils.AMapUtil;
import com.hy.location.utils.LoadDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;

public class MainPage extends AppCompatActivity implements View.OnClickListener, RouteSearch.OnRouteSearchListener {

    private static final String TAG = "MainPage";
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
    private TextView tv_input, tv_save, tv_share;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        mContext = MainPage.this;
        mRealm = Realm.getDefaultInstance();
        initToolbar();
        initView();
        setRouteList();

        boolean granted = checkPermissionAllGranted(new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
        });

        if (!granted) {
            requestPermission();
        }
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
        ld = new LoadDialog(mContext);
        recyclerView_line = (RecyclerView) findViewById(R.id.recyclerView_line);
        recyclerView_line_dot = (RecyclerView) findViewById(R.id.recyclerView_line_dot);
        tv_pointName = (TextView) findViewById(R.id.tv_pointName);
        tv_input = (TextView) findViewById(R.id.tv_input);
        tv_input.setOnClickListener(this);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_save.setOnClickListener(this);
        tv_share = (TextView) findViewById(R.id.tv_share);
        tv_share.setOnClickListener(this);
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
        mStartLatlng = new LatLonPoint(Double.valueOf(currLineBean.getBeginLat()), Double.valueOf(currLineBean.getBeginLng()));
        mEndLatlng = new LatLonPoint(Double.valueOf(currLineBean.getEndLat()), Double.valueOf(currLineBean.getEndLng()));
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartLatlng, mEndLatlng);
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DRIVING_SINGLE_DEFAULT, null,
                null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
        mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
    }

    private void deleteData() {
        RealmResults<LineBean> list1 = mRealm.where(LineBean.class).findAll();
        RealmResults<PointBean> list2 = mRealm.where(PointBean.class).findAll();
        mRealm.executeTransaction(realm -> {
            list1.deleteAllFromRealm();
            list2.deleteAllFromRealm();
        });
        currLineBean = null;
    }

    private void setRouteList() {
        list_line = mRealm.where(LineBean.class).findAll();
        mRouteAdapter = new RouteAdapter(R.layout.list_route_item, list_line);
        recyclerView_line.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView_line.setAdapter(mRouteAdapter);
        mRouteAdapter.setOnItemClickListener((adapter, view, position) -> {
            currLineBean = list_line.get(position);
            tv_pointName.setEnabled(true);
            tv_pointName.setText(list_line.get(position).getRouteName());
            searchLocal();
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
        if (ld.isShowing()) {
            ld.cancel();
        }
        if (errorCode == 1000) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    mDriveRouteResult = driveRouteResult;
                    mDrivePath = mDriveRouteResult.getPaths().get(0);
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

    private void insertDB() {
        if (mDrivePath != null) {
            List<DriveStep> steps = mDrivePath.getSteps();
            for (int i = 0; i < steps.size(); i++) {
                DriveStep driveStep = steps.get(i);
                List<LatLonPoint> polyline = driveStep.getPolyline();

                mRealm.beginTransaction();
                PointBean bean = new PointBean();
                bean.setId(UUID.randomUUID().toString().replace("-", ""));
                bean.setRouteID(currLineBean.getId());
                bean.setPointIndex("" + i);
                bean.setPointName(driveStep.getInstruction());
                bean.setNextPointRange(driveStep.getDistance());
                bean.setLongitude(polyline.get(0).getLongitude());
                bean.setLatitude(polyline.get(0).getLatitude());

                mRealm.insert(bean);
                mRealm.commitTransaction();
                Toast.makeText(mContext, "已保存", Toast.LENGTH_SHORT).show();
            }
        }
        if (ld.isShowing()) {
            ld.cancel();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_input:
                deleteData();
                String json = readFromSD("haoyun.txt");
                if (TextUtils.isEmpty(json)) {
                    Toast.makeText(mContext, "无法获取文件 haoyun.txt", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        LineBean bean = new LineBean();
                        if (jsonObject != null) {
                            String id = jsonObject.optString("id");
                            String routeName = jsonObject.optString("RouteName");
                            String beginLngLat = jsonObject.optString("BeginLngLat");
                            String endLngLat = jsonObject.optString("EndLngLat");
                            bean.setId(id);
                            bean.setRouteName(routeName);
                            int len1 = beginLngLat.indexOf(",");
                            int len2 = endLngLat.indexOf(",");
                            if (len1 > 0 && len2 > 0) {
                                bean.setBeginLng(beginLngLat.substring(0, len1));
                                bean.setBeginLat(beginLngLat.substring(len1 + 1, beginLngLat.length()));
                                bean.setEndLng(endLngLat.substring(0, len1));
                                bean.setEndLat(endLngLat.substring(len1 + 1, endLngLat.length()));
                            }
                        }
                        mRealm.beginTransaction();
                        mRealm.insert(bean);
                        mRealm.commitTransaction();

                        String path = Environment.getExternalStorageDirectory().getCanonicalPath() + "/haoyun.txt";
                        Toast.makeText(mContext, "导入文件" + path, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                list_line = mRealm.where(LineBean.class).findAll();
                mRouteAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_save:
                if (currLineBean != null && currLineBean.getId() != null) {
                    RealmResults<PointBean> pointList = mRealm.where(PointBean.class)
                            .equalTo("routeID", currLineBean.getId())
                            .findAllSorted("pointIndex");

                    if (pointList != null && pointList.size() > 0) {
                        mRealm.executeTransaction(realm -> pointList.deleteAllFromRealm());
                    }

                    insertDB();
                }

                break;
            case R.id.tv_share:
                saveJson();
                break;
            default:
                break;
        }
    }

    private void saveJson() {
        RealmResults<PointBean> list = mRealm.where(PointBean.class).findAll();
        RealmResults<PointBean> list1 = list.sort(new String[]{"routeID", "pointIndex"}, new Sort[]{Sort.ASCENDING, Sort.ASCENDING});
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list1.size(); i++) {
            PointBean pointBean = list1.get(i);
            JSONObject jsonObject = new JSONObject();
            Log.e("PointName", pointBean.getPointName());
            Log.e("PointIndex", pointBean.getPointIndex() + "");
            Log.e("RouteID", pointBean.getRouteID() + "");
            try {
                jsonObject.put("id", pointBean.getId());
                jsonObject.put("RouteID", pointBean.getRouteID());
                jsonObject.put("PointIndex", pointBean.getPointIndex());
                jsonObject.put("PointName", pointBean.getPointName());
                jsonObject.put("NextPointRange", pointBean.getNextPointRange());
                jsonObject.put("Longitude", pointBean.getLongitude());
                jsonObject.put("Latitude", pointBean.getLatitude());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Log.e("jsonArray", jsonArray.toString());
        saveFile(jsonArray.toString());
    }

    //读取SD卡中文件的方法
    public String readFromSD(String filename) {

        StringBuilder sb = new StringBuilder("");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + filename;
                FileInputStream input = new FileInputStream(filename);
                InputStreamReader reader = new InputStreamReader(input, "UTF-8");
                BufferedReader br = new BufferedReader(reader);
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                reader.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public void saveFile(String str) {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        try {
            if (hasSDCard) {
                filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + "Route.txt";
            } else {
                filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + "Route.txt";
            }
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            int len = str.getBytes().length;
            Log.e("len===", len + "");
            outStream.write(str.getBytes());
            outStream.close();
            Toast.makeText(mContext, "已保存到 " + filePath, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}