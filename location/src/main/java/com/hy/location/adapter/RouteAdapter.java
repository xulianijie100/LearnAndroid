package com.hy.location.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hy.location.R;
import com.hy.location.bean.LineBean;
import com.hy.location.bean.LocalBean;
import com.hy.location.view.LocalViewHolder;
import com.hy.location.view.RouteViewHolder;

import java.util.List;

/**
 * Created by xulj on 2018/4/17.
 */

public class RouteAdapter extends BaseQuickAdapter<LineBean, RouteViewHolder> {
    public RouteAdapter(int layoutResId, @Nullable List<LineBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(RouteViewHolder helper, LineBean item) {

        if (item != null) {
            helper.setText(R.id.tv_name, item.getPointName());
        }
    }
}
