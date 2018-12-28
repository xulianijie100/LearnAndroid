package com.hy.android.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.BindView;
import com.hy.android.Component.ApplicationComponent;
import com.hy.android.base.BaseActivity;
import com.hy.android.R;
import com.hy.android.bean.PieData;
import com.hy.android.view.CanvasView;
import com.hy.android.view.PathView;
import com.hy.android.view.PieView;

import java.util.ArrayList;

public class ViewActivity extends BaseActivity {

    @BindView(R.id.layout_content1)
    RelativeLayout relativeLayout1;

    @BindView(R.id.layout_content2)
    RelativeLayout relativeLayout2;

    @BindView(R.id.layout_content3)
    RelativeLayout relativeLayout3;

    private PieView pieView;

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
        pieView = new PieView(this);
        relativeLayout1.addView(pieView);
        relativeLayout2.addView(new CanvasView(this));
        relativeLayout3.addView(new PathView(this));
    }

    @Override
    public void initData() {
        ArrayList<PieData> datas = new ArrayList<>();
        PieData pieData = new PieData("PieView", 60);
        PieData pieData2 = new PieData("PieView", 30);
        PieData pieData3 = new PieData("PieView", 40);
        PieData pieData4 = new PieData("PieView", 20);
        PieData pieData5 = new PieData("PieView", 20);
        datas.add(pieData);
        datas.add(pieData2);
        datas.add(pieData3);
        datas.add(pieData4);
        datas.add(pieData5);
        pieView.setData(datas);

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
