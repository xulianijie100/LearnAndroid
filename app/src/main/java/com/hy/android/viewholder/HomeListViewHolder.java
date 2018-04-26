package com.hy.android.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hy.android.R;

/**
 * Created by Administrator on 2018/4/17.
 */

public class HomeListViewHolder extends BaseViewHolder {

    public TextView homeItemAuthor;
    public TextView homeItemDate;
    public TextView homeItemTitle;
    public TextView homeItemType;
    public ImageView homeItemLike;

    public HomeListViewHolder(View view) {
        super(view);
        homeItemAuthor=view.findViewById(R.id.homeItemAuthor);
        homeItemDate=view.findViewById(R.id.homeItemDate);
        homeItemTitle=view.findViewById(R.id.homeItemTitle);
        homeItemType=view.findViewById(R.id.homeItemType);
        homeItemLike=view.findViewById(R.id.homeItemLike);
    }
}
