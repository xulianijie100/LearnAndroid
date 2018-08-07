package com.hy.location.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.hy.location.R;
import com.hy.location.bean.LineBean;

import java.util.List;

import io.realm.Realm;


public class LocalActivity extends AppCompatActivity implements Inputtips.InputtipsListener {
    private static final String TAG = "LocalActivity";
    private static final int MY_PERMISSION_REQUEST_CODE = 1000;
    private EditText edit_local1;
    private EditText edit_local2;
    private Context mContext;
    private Button btn_add;
    private EditText edit_local;
    private String edit_str = "";

    private Realm mRealm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_local);

        initToolbar();
        initView();
    }

    private void initView() {
        mContext = LocalActivity.this;
        mRealm = Realm.getDefaultInstance();
        btn_add = (Button) findViewById(R.id.btn_add);
        edit_local = (EditText) findViewById(R.id.edit_local);
        edit_local1 = (EditText) findViewById(R.id.edit_local_1);
        edit_local2 = (EditText) findViewById(R.id.edit_local_2);


        edit_local1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text=s.toString();
                if(!TextUtils.isEmpty(text)){
                    tipsQuery(text);
                }
            }
        });



        btn_add.setOnClickListener(v -> {
            edit_str = edit_local.getText().toString();
            String edit_str1 = edit_local1.getText().toString();
            String edit_str2 = edit_local2.getText().toString();

            if (TextUtils.isEmpty(edit_str)) {
                Toast.makeText(mContext, "线路名称不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            LineBean bean = new LineBean();
            bean.setRouteName(edit_str);
            bean.setRouteName(edit_str1 + "--->" + edit_str2);
            mRealm.beginTransaction();
            mRealm.copyToRealm(bean);
            mRealm.commitTransaction();

            finish();
        });
    }

    private void tipsQuery(String str){
        InputtipsQuery inputquery = new InputtipsQuery(str, "广州");
        inputquery.setCityLimit(false);//限制在当前城市
        Inputtips inputTips = new Inputtips(LocalActivity.this, inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void onGetInputtips(List<Tip> list, int code) {
        Log.e("code==",code+"");
        if(list!=null && list.size()>0){
            for(Tip tip:list){
                Log.e("tip==",tip.getAddress());
                Log.e("tip==",tip.getPoint().getLongitude()+"---"+tip.getPoint().getLatitude());
            }
        }

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.str_add_route);
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
}
