package com.hy.android.fragment;

import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.hy.android.Base.BaseFragment;
import com.hy.android.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/17.
 */

public class TypeFragment extends BaseFragment {


    @BindView(R.id.recyclerView_dot)
    RecyclerView recyclerView_dot;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

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
}
