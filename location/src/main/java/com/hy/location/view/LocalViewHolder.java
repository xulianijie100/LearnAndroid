package com.hy.location.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hy.location.R;

/**
 * Created by Administrator on 2018/4/17.
 */

public class LocalViewHolder extends BaseViewHolder {

    public TextView tv_name;
    public TextView tv_local;
    public LocalViewHolder(View view) {
        super(view);
        tv_name=view.findViewById(R.id.tv_name);
        tv_local=view.findViewById(R.id.tv_local);
    }
}
