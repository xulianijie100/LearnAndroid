package com.hy.android.ui.news;

import android.os.Bundle;
import android.view.View;

import com.hy.android.R;
import com.hy.android.component.ApplicationComponent;
import com.hy.android.base.BaseFragment;
import com.hy.android.bean.NewsDetail;
import com.hy.android.component.DaggerHttpComponent;
import com.hy.android.ui.news.contract.DetailContract;
import com.hy.android.ui.news.presenter.DetailPresenter;

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
        return R.layout.fragment_detail;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .inject(this);
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
