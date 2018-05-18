package com.hy.android.fragment;

import android.view.View;
import android.widget.TextView;

import com.hy.android.Base.BaseFragment;
import com.hy.android.R;

import butterknife.BindView;
import butterknife.OnClick;

public class TypeFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.tv_01)
    TextView tv_01;

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

    @OnClick({R.id.tv_01})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_01:
                break;
            default:
                break;
        }
    }
}
