package com.hy.android.ui.news;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hy.android.R;
import com.hy.android.adapter.NewsDetailAdapter;
import com.hy.android.component.ApplicationComponent;
import com.hy.android.base.BaseFragment;
import com.hy.android.bean.NewsDetail;
import com.hy.android.component.DaggerHttpComponent;
import com.hy.android.net.NewsApi;
import com.hy.android.ui.news.contract.DetailContract;
import com.hy.android.ui.news.presenter.DetailPresenter;
import com.hy.android.widget.NewsDelPop;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class DetailFragment extends BaseFragment<DetailPresenter> implements DetailContract.View {


    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrFrameLayout mPtrFrameLayout;
    @BindView(R.id.tv_toast)
    TextView mTvToast;
    @BindView(R.id.rl_top_toast)
    RelativeLayout mRlTopToast;

    private View view_Focus;//顶部banner
    private Banner mBanner;
    private NewsDelPop newsDelPop;
    private String newsid;
    private int position;
    private List<NewsDetail.ItemBean> beanList;
    private List<NewsDetail.ItemBean> mBannerList;
    private NewsDetailAdapter detailAdapter;
    private int upPullNum = 1;
    private int downPullNum = 1;
    private boolean isRemoveHeaderView = false;

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
        if (getArguments() == null) return;
        newsid = getArguments().getString("newsid");
        position = getArguments().getInt("position");
        mPresenter.getData(newsid, NewsApi.ACTION_DEFAULT, 1);
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
