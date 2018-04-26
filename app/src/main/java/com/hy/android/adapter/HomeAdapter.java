package com.hy.android.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hy.android.R;
import com.hy.android.bean.HomeData;
import com.hy.android.viewholder.HomeListViewHolder;

import java.util.List;

/**
 * Created by xulj on 2018/4/17.
 */

public class HomeAdapter extends BaseQuickAdapter<HomeData, HomeListViewHolder> {
    public HomeAdapter(int layoutResId, @Nullable List<HomeData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(HomeListViewHolder helper, HomeData item) {
        if (!TextUtils.isEmpty(item.author)) {
            helper.setText(R.id.homeItemAuthor, item.author);
            helper.setText(R.id.homeItemDate, item.niceDate);
            helper.setText(R.id.homeItemTitle, item.title);
            helper.setText(R.id.homeItemType, item.chapterName);
        }
    }
}
