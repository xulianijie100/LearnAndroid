package com.hy.android.ui.video;

import android.os.Bundle;
import android.view.View;

import com.hy.android.R;
import com.hy.android.base.BaseFragment;
import com.hy.android.base.SupportFragment;

public class VideoFragment extends BaseFragment {

    public static SupportFragment newInstance() {
        Bundle args = new Bundle();
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_news_new;
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }
}
