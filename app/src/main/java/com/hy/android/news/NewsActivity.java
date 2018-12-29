package com.hy.android.news;

import android.os.Bundle;
import android.view.View;
import com.hy.android.Component.ApplicationComponent;
import com.hy.android.base.BaseActivity;
import com.hy.android.bean.Channel;
import com.hy.android.news.contract.NewsContract;
import com.hy.android.news.presenter.NewsPresenter;

import java.util.List;

public class NewsActivity extends BaseActivity<NewsPresenter> implements NewsContract.View {

    private static final String TAG = "NewsActivity";

    @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return 0;
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

    @Override
    public void loadData(List<Channel> channels, List<Channel> otherChannels) {

    }
}
