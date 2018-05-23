package com.hy.location;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hy.location.activity.LocalActivity;
import com.hy.location.adapter.LocalAdapter;
import com.hy.location.bean.LocalBean;

import java.io.InputStream;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    RecyclerView recyclerView;

    ProgressDialog progressDialog;

    private Context mContext;

    private LocalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=MainActivity.this;
        initView();
        initToolbar();
    }

    private void initView(){
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        progressDialog = new ProgressDialog(this);

        new ExcelDataLoader().execute("test1.xls");
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.app_name);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }
    /**
     * 获取 excel 表格中的数据,不能在主线程中调用
     *
     * @param xlsName excel 表格的名称
     * @param index   第几张表格中的数据
     */
    private ArrayList<LocalBean> getXlsData(String xlsName, int index) {
        ArrayList<LocalBean> countryList = new ArrayList<>();
        try {
            InputStream is = mContext.getAssets().open(xlsName);
            Workbook workbook = Workbook.getWorkbook(is);
            Sheet sheet = workbook.getSheet(index);

            int sheetRows = sheet.getRows();
            int sheetColumns = sheet.getColumns();

            Log.e(TAG, "total rows is 行=" + sheetRows);
            Log.e(TAG, "total cols is 列=" + sheetColumns);

            for (int i = 0; i < sheetRows; i++) {
                LocalBean bean = new LocalBean();
                bean.name = (sheet.getCell(0, i).getContents());
                for(int j=1;j<sheetColumns;j++){
                    bean.localArray.add(sheet.getCell(j, i).getContents());
                }
                countryList.add(bean);
            }
            is.close();
            workbook.close();

        } catch (Exception e) {
            Log.e(TAG, "read error=" + e, e);
        }

        return countryList;
    }


    //在异步方法中 调用
    private class ExcelDataLoader extends AsyncTask<String, Void, ArrayList<LocalBean>> {

        @Override
        protected void onPreExecute() {
            Log.e(TAG, " onPreExecute ----" );
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
                Log.e("list==",list.size()+"");
                for(LocalBean bean:list){
                    Log.e("list==",bean.name+"");
                }
                //存在数据
                adapter = new LocalAdapter(R.layout.list_item, list);
                recyclerView.setAdapter(adapter);
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

}
