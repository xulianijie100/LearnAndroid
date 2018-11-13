package com.hy.android.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hy.android.R;
import com.hy.android.bean.HomeData;
import com.hy.android.viewholder.FuncListViewHolder;
import com.hy.android.viewholder.HomeListViewHolder;

import java.util.List;

/**
 * Created by xulj on 2018/4/17.
 */

public class FuncAdapter extends BaseQuickAdapter<String, FuncListViewHolder> {
    public FuncAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(FuncListViewHolder helper, String item) {
        if (!TextUtils.isEmpty(item)) {
            helper.setText(R.id.tv_name, item);
        }
    }
}
