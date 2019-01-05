package com.hy.android.ui.jandan;

import android.os.Bundle;
import android.view.View;

import com.hy.android.R;
import com.hy.android.base.BaseFragment;
import com.hy.android.base.SupportFragment;
import com.hy.android.component.ApplicationComponent;

public class JanDanFragment extends BaseFragment {

    public static SupportFragment newInstance() {
        Bundle args = new Bundle();
        JanDanFragment fragment = new JanDanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_news_new;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }
}
