package com.hy.android.fragment;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;

import com.hy.android.Base.BaseFragment;
import com.hy.android.R;
import com.hy.android.activity.*;
import com.hy.android.activity.AsyncActivity;

public class TypeFragment extends BaseFragment implements View.OnClickListener {


    private CardView cardView_01,cardView_02,cardView_03,
            cardView_04,cardView_05;

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
        cardView_03 = mView.findViewById(R.id.cardView_03);
        cardView_04 = mView.findViewById(R.id.cardView_04);
        cardView_05 = mView.findViewById(R.id.cardView_05);
        cardView_01.setOnClickListener(this);
        cardView_02.setOnClickListener(this);
        cardView_03.setOnClickListener(this);
        cardView_04.setOnClickListener(this);
        cardView_05.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardView_01:
                startActivity(new Intent(getActivity(), AsyncActivity.class));
                break;
            case R.id.cardView_02:
                startActivity(new Intent(getActivity(), VideoActivity.class));
                break;
            case R.id.cardView_03:
                startActivity(new Intent(getActivity(), ViewActivity.class));
                break;
            case R.id.cardView_04:
                startActivity(new Intent(getActivity(), AnimationActivity.class));
                break;
            case R.id.cardView_05:
                startActivity(new Intent(getActivity(), TestFunActivity.class));
                break;
            default:
                break;
        }
    }
}
