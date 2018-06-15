package com.hy.location;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hy.location.activity.LocalActivity;
import com.hy.location.adapter.LocalAdapter;
import com.hy.location.bean.LocalBean;
import com.hy.location.utils.RefreshEvent;
import com.hy.location.utils.SaveListUtil;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    RecyclerView recyclerView;

    ProgressDialog progressDialog;

    private Context mContext;

    private LocalAdapter adapter;

    private ArrayList<LocalBean> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolbar();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        mContext = MainActivity.this;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        progressDialog = new ProgressDialog(this);

        list_data = SaveListUtil.getStorageEntities("haoyun.txt");
        if (list_data.size() == 0) {
            new ExcelDataLoader().execute("hy.xls");
        }
        adapter = new LocalAdapter(R.layout.list_item,list_data);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent=new Intent(mContext,LocalActivity.class);
            intent.putExtra("line",list_data.get(position));
            startActivity(intent);
        });

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshEvent event) {
        list_data.clear();
        list_data.addAll(event.list);
        adapter.notifyDataSetChanged();
    }


    /**
     * 获取 excel 表格中的数据,不能在主线程中调用
     *
     * @param xlsName excel 表格的名称
     * @param index   第几张表格中的数据
     */
    private ArrayList<LocalBean> getXlsData(String xlsName, int index) {
        ArrayList<LocalBean> countryList = new ArrayList<>();
        HSSFWorkbook wb = null;
        POIFSFileSystem fs = null;
        String path = Environment.getExternalStorageDirectory().toString() + "/" + xlsName;
        Log.e("path==", path);
        try {
            fs = new POIFSFileSystem(new FileInputStream(path));
            wb = new HSSFWorkbook(fs);
            Sheet sheet = wb.getSheetAt(0);

            int rownum = sheet.getLastRowNum();

            Log.e(TAG, "total rows is 行=" + rownum);

            for (int i = 0; i <= rownum; i++) {
                LocalBean bean = new LocalBean();
                Row row = sheet.getRow(i);
                short cellnum = row.getLastCellNum();
                for (int j = row.getFirstCellNum(); j < cellnum; j++) {
                    Cell celldata = row.getCell(j);
                    String data = celldata + "";
                    bean.localArray.add(data);
                }
                countryList.add(bean);
            }
            wb.close();
            fs.close();

        } catch (Exception e) {
            Log.e(TAG, "read error=" + e, e);
        }
        return countryList;
    }


    //在异步方法中 调用
    private class ExcelDataLoader extends AsyncTask<String, Void, ArrayList<LocalBean>> {

        @Override
        protected void onPreExecute() {
            Log.e(TAG, " onPreExecute ----");
            progressDialog.setMessage("加载中,请稍后......");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected ArrayList<LocalBean> doInBackground(String... params) {
            return getXlsData(params[0], 0);
        }

        @Override
        protected void onPostExecute(ArrayList<LocalBean> list) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (list != null && list.size() > 0) {
                //存在数据
                SaveListUtil.saveList2SDCard(list,"haoyun.txt");
                ArrayList<LocalBean> storageEntities = SaveListUtil.getStorageEntities("haoyun.txt");
                list_data.addAll(storageEntities);
                adapter.notifyDataSetChanged();

            } else {
                //加载失败
            }

        }
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
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
