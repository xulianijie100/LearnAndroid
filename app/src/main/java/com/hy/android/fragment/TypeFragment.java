package com.hy.android.fragment;

import android.view.View;

import com.hy.android.Base.BaseFragment;
import com.hy.android.R;

public class TypeFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_type;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
