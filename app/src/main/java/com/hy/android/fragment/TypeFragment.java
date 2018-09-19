package com.hy.android.fragment;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;

import com.hy.android.Base.BaseFragment;
import com.hy.android.R;
import com.hy.android.activity.RxJavaActivity;
import com.hy.android.activity.TestActivity;

public class TypeFragment extends BaseFragment implements View.OnClickListener {


    private CardView cardView_01;
    private CardView cardView_02;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_type;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        cardView_01 = mView.findViewById(R.id.cardView_01);
        cardView_02 = mView.findViewById(R.id.cardView_02);
        cardView_01.setOnClickListener(this);
        cardView_02.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardView_01:
                startActivity(new Intent(getActivity(), RxJavaActivity.class));
                break;
            case R.id.cardView_02:
                startActivity(new Intent(getActivity(), TestActivity.class));
                break;
            default:
                break;
        }
    }
}
