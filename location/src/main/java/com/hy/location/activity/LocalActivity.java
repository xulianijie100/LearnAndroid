package com.hy.location.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.hy.location.R;
import com.hy.location.adapter.RecyclerViewAdapter;
import com.hy.location.bean.LineBean;
import com.hy.location.view.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;


public class LocalActivity extends AppCompatActivity implements Inputtips.InputtipsListener {
    private static final String TAG = "LocalActivity";
    private EditText edit_local1;
    private EditText edit_local2;
    private Context mContext;
    private Button btn_add;
    private EditText edit_local;
    private String edit_str = "";
    private RecyclerView recyclerView_tip1, recyclerView_tip2;
    private RecyclerViewAdapter<Tip> adapter1, adapter2;
    private List<Tip> list_tip1;
    private List<Tip> list_tip2;
    private Realm mRealm;
    private int currEdit = 1;
    private boolean queryFlag = true;
    private Tip start_tip, end_tip;

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
        list_tip1 = new ArrayList<>();
        list_tip2 = new ArrayList<>();
        btn_add = (Button) findViewById(R.id.btn_add);
        edit_local = (EditText) findViewById(R.id.edit_local);
        edit_local1 = (EditText) findViewById(R.id.edit_local_1);
        edit_local2 = (EditText) findViewById(R.id.edit_local_2);
        recyclerView_tip1 = (RecyclerView) findViewById(R.id.recyclerView_tip1);
        recyclerView_tip2 = (RecyclerView) findViewById(R.id.recyclerView_tip2);
        recyclerView_tip1.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView_tip2.setLayoutManager(new LinearLayoutManager(mContext));

        adapter1 = new RecyclerViewAdapter<Tip>(list_tip1) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.list_item_tip;
            }

            @Override
            protected void bindData(RecyclerViewHolder holder, int viewType, int position, Tip item) {
                holder.setText(R.id.tv_name, item.getName());
                if (TextUtils.isEmpty(item.getAddress())) {
                    holder.setText(R.id.tv_address, item.getDistrict());
                } else {
                    holder.setText(R.id.tv_address, item.getAddress());
                }
            }
        };

        adapter1.setItemClickListener((view, position) -> {
            start_tip = adapter1.getData().get(position);
            queryFlag = false;
            edit_local1.setText(start_tip.getName());
            edit_local1.setSelection(start_tip.getName().length());
            recyclerView_tip1.setVisibility(View.GONE);

        });

        adapter2 = new RecyclerViewAdapter<Tip>(list_tip2) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.list_item_tip;
            }

            @Override
            protected void bindData(RecyclerViewHolder holder, int viewType, int position, Tip item) {
                holder.setText(R.id.tv_name, item.getName());
                if (TextUtils.isEmpty(item.getAddress())) {
                    holder.setText(R.id.tv_address, item.getDistrict());
                } else {
                    holder.setText(R.id.tv_address, item.getAddress());
                }

            }
        };
        adapter2.setItemClickListener((view, position) -> {
            end_tip = adapter2.getData().get(position);
            queryFlag = false;
            edit_local2.setText(end_tip.getName());
            edit_local2.setSelection(end_tip.getName().length());
            recyclerView_tip2.setVisibility(View.GONE);

        });

        recyclerView_tip1.setAdapter(adapter1);
        recyclerView_tip2.setAdapter(adapter2);

        edit_local1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString().trim();
                if (!TextUtils.isEmpty(text) && queryFlag) {
                    tipsQuery(text, 1);
                } else {
                    recyclerView_tip1.setVisibility(View.GONE);
                    recyclerView_tip2.setVisibility(View.GONE);
                    queryFlag = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_local2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString().trim();
                if (!TextUtils.isEmpty(text) && queryFlag) {
                    tipsQuery(text, 2);
                } else {
                    recyclerView_tip1.setVisibility(View.GONE);
                    recyclerView_tip2.setVisibility(View.GONE);
                    queryFlag = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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

            if (TextUtils.isEmpty(edit_str1) || TextUtils.isEmpty(edit_str2)) {
                Toast.makeText(mContext, "请选择位置", Toast.LENGTH_SHORT).show();
                return;
            }

            LineBean bean = new LineBean();
            bean.setRouteName(edit_str);
            bean.setId(UUID.randomUUID().toString().replace("-", ""));
            if(start_tip!=null && end_tip!=null){
                bean.setBeginLng(start_tip.getPoint().getLongitude()+"");
                bean.setBeginLat(start_tip.getPoint().getLatitude()+"");
                bean.setEndLng(end_tip.getPoint().getLongitude()+"");
                bean.setEndLat(end_tip.getPoint().getLatitude()+"");
            }
            mRealm.beginTransaction();
            mRealm.copyToRealm(bean);
            mRealm.commitTransaction();

            finish();

        });
    }

    private void tipsQuery(String str, int flag) {
        currEdit = flag;
        InputtipsQuery inputquery = new InputtipsQuery(str, "广州");
        inputquery.setCityLimit(false);//限制在当前城市
        Inputtips inputTips = new Inputtips(LocalActivity.this, inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void onGetInputtips(List<Tip> list, int code) {
        Log.e("code==", code + "");
        if (list != null && list.size() > 0) {
            if (currEdit == 1) {
                recyclerView_tip1.setVisibility(View.VISIBLE);
                list_tip1.clear();
                list_tip1.addAll(list);
                adapter1.notifyDataSetChanged();
            } else if (currEdit == 2) {
                recyclerView_tip2.setVisibility(View.VISIBLE);
                list_tip2.clear();
                list_tip2.addAll(list);
                adapter2.notifyDataSetChanged();
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
