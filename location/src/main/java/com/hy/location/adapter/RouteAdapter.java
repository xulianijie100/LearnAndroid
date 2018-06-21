package com.hy.location.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hy.location.R;
import com.hy.location.bean.LineBean;
import com.hy.location.bean.LocalBean;
import com.hy.location.view.LocalViewHolder;
import com.hy.location.view.RouteViewHolder;

import java.util.List;

public class RouteAdapter extends BaseQuickAdapter<LineBean, RouteViewHolder> {
    public RouteAdapter(int layoutResId, @Nullable List<LineBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(RouteViewHolder helper, LineBean item) {

        if (item != null) {
            if(item.getRouteName().length()>6){
                helper.setText(R.id.tv_name,item.getRouteName().substring(0,5));
            }else {
                helper.setText(R.id.tv_name,item.getRouteName());
            }
        }
    }
}
