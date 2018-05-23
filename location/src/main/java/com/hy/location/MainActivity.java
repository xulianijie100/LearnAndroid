package com.hy.location;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        progressDialog = new ProgressDialog(this);

        new ExcelDataLoader().execute("test1.xls");
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


}
