package com.hy.android.viewholder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hy.android.R;

public class FuncListViewHolder extends BaseViewHolder {

    public CardView cv_name;
    public TextView tv_name;
    public FuncListViewHolder(View view) {
        super(view);
        cv_name = view.findViewById(R.id.cv_name);
        tv_name = view.findViewById(R.id.tv_name);
    }
}
