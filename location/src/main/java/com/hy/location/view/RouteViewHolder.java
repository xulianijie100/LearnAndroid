package com.hy.location.view;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hy.location.R;

/**
 * Created by Administrator on 2018/4/17.
 */

public class RouteViewHolder extends BaseViewHolder {

    public TextView tv_name;
    public RouteViewHolder(View view) {
        super(view);
        tv_name= (TextView) view.findViewById(R.id.tv_name);
    }
}
