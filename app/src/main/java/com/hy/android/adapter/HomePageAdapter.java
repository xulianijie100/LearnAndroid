package com.hy.android.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hy.android.R;
import com.hy.android.mvp.entity.HomeEntity;
import com.hy.android.viewholder.HomeListViewHolder;

import java.util.List;

public class HomePageAdapter extends BaseQuickAdapter<HomeEntity.DataBean.DatasBean, HomeListViewHolder> {
    public HomePageAdapter(int layoutResId, @Nullable List<HomeEntity.DataBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(HomeListViewHolder helper, HomeEntity.DataBean.DatasBean item) {
        helper.setText(R.id.homeItemAuthor, item.author);
        helper.setText(R.id.homeItemDate, item.niceDate);
        helper.setText(R.id.homeItemTitle, item.title);
        helper.setText(R.id.homeItemType, item.chapterName);

    }
}
