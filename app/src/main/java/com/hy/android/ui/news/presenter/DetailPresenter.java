package com.hy.android.ui.news.presenter;

import com.hy.android.base.BasePresenter;
import com.hy.android.net.NewsApi;
import com.hy.android.ui.news.contract.DetailContract;

import javax.inject.Inject;

public class DetailPresenter extends BasePresenter<DetailContract.View> implements DetailContract.Presenter {
    private static final String TAG = "DetailPresenter";

    NewsApi mNewsApi;

    @Inject
    public DetailPresenter(NewsApi newsApi) {
        this.mNewsApi = newsApi;
    }

    @Override
    public void getData(final String id, final String action, int pullNum) {



    }
}
