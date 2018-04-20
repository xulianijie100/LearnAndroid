package com.hy.android.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hy.android.R;
import com.hy.android.bean.BannerData;
import com.hy.android.bean.HomeData;
import com.hy.android.viewholder.BannerViewHolder;
import com.hy.android.viewholder.KnowledgeListViewHolder;

import java.util.List;

/**
 * Created by xulj on 2018/4/17.
 */

public class BannerAdapter extends BaseQuickAdapter<BannerData, BannerViewHolder> {

    private Context mContext;

    public BannerAdapter(Context context,int layoutResId, @Nullable List<BannerData> data) {
        super(layoutResId, data);
        this.mContext=context;
    }

    @Override
    protected void convert(BannerViewHolder helper, BannerData item) {
        if (!TextUtils.isEmpty(item.title)) {
            helper.setText(R.id.bannerTitle, item.title);
            Glide.with(mContext).load(item.imagePath).into(helper.bannerImage);
        }
    }
}
