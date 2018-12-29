package com.hy.android.news;

import android.os.Bundle;
import android.view.View;
import com.hy.android.Component.ApplicationComponent;
import com.hy.android.base.BaseFragment;
import com.hy.android.bean.NewsDetail;
import com.hy.android.news.contract.DetailContract;
import com.hy.android.news.presenter.DetailPresenter;

import java.util.List;

public class DetailFragment extends BaseFragment<DetailPresenter> implements DetailContract.View {


    public static DetailFragment newInstance(String newsid, int position) {
        Bundle args = new Bundle();
        args.putString("newsid", newsid);
        args.putInt("position", position);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
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
    public void loadBannerData(NewsDetail newsDetail) {

    }

    @Override
    public void loadTopNewsData(NewsDetail newsDetail) {

    }

    @Override
    public void loadData(List<NewsDetail.ItemBean> itemBeanList) {

    }

    @Override
    public void loadMoreData(List<NewsDetail.ItemBean> itemBeanList) {

    }
}
