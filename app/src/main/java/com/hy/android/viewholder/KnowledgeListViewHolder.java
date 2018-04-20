package com.hy.android.viewholder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hy.android.R;

/**
 * Created by Administrator on 2018/4/17.
 */

public class KnowledgeListViewHolder extends BaseViewHolder {

    public TextView homeItemAuthor;

    public KnowledgeListViewHolder(View view) {
        super(view);
        homeItemAuthor=view.findViewById(R.id.homeItemAuthor);
    }
}
