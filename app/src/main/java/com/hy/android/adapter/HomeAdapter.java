package com.hy.android.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hy.android.R;
import com.hy.android.bean.HomeData;
import com.hy.android.viewholder.KnowledgeListViewHolder;

import java.util.List;

/**
 * Created by xulj on 2018/4/17.
 */

public class HomeAdapter extends BaseQuickAdapter<HomeData,KnowledgeListViewHolder> {
    public HomeAdapter(int layoutResId, @Nullable List<HomeData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(KnowledgeListViewHolder helper, HomeData item) {
        if (!TextUtils.isEmpty(item.author)) {
            helper.setText(R.id.homeItemAuthor, item.author);
        }
    }
}
