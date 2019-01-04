package com.hy.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hy.android.component.ApplicationComponent;
import com.hy.android.base.BaseFragment;
import com.hy.android.R;
import com.hy.android.activity.ArticleDetailActivity;
import com.hy.android.adapter.HomeAdapter;
import com.hy.android.bean.BannerData;
import com.hy.android.bean.BaseResponse;
import com.hy.android.bean.HomeData;
import com.hy.android.bean.HomeDataList;
import com.hy.android.net.BaseObserver;
import com.hy.android.net.RetrofitHelper;
import com.hy.android.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/17.
 */

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private List<HomeData> homeDatas;
    private List<BannerData> bannerDatas;
    private Banner mBanner;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState){
        homeDatas = new ArrayList<>();
        bannerDatas = new ArrayList<>();

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        //banner
        LinearLayout mHeaderGroup = ((LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.home_banner, null));
        mBanner = mHeaderGroup.findViewById(R.id.head_banner);
        mHeaderGroup.removeView(mBanner);
        //list
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mAdapter = new HomeAdapter(R.layout.home_list_item, homeDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.addHeaderView(mBanner);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                int page = mAdapter.getData().size()/ 20 + 1;
                 getHomeList(page);
            }
        },mRecyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        getBanner();
        getHomeList(0);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleDetailActivity.startActivity(getActivity(), homeDatas.get(position).title, homeDatas.get(position).link);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void refreshData() {
        swipeRefreshLayout.setRefreshing(true);
        mAdapter.setEnableLoadMore(false);
        getHomeList(0);
    }

    private void getBanner() {
        RetrofitHelper.getInstance().getApiService().getBannerData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<List<BannerData>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<BannerData>> response) {
                        if (response.getErrorCode() == 0 && response.getData().size() > 0) {
                            bannerDatas.clear();
                            bannerDatas.addAll(response.getData());
                            showBannerData(bannerDatas);
                        } else {
                            toast(response.getErrorMsg());
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }
                });
    }

    private void getHomeList(final int page) {
        RetrofitHelper.getInstance().getApiService().getHomeList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<HomeDataList>>() {
                    @Override
                    public void onSuccess(BaseResponse<HomeDataList> response) {
                        if (response.getErrorCode() == 0 && response.getData() != null) {

                            int total = response.getData().total;

                            if(total==0){return;}

                            if (total < response.getData().size) {
                                mAdapter.replaceData(response.getData().datas);
                                mAdapter.loadMoreComplete();
                                mAdapter.loadMoreEnd();
                                mAdapter.setEnableLoadMore(false);
                                return;
                            }

                            Log.e("data==",total+"--"+response.getData().size);

                            if (response.getData().offset >= total || response.getData().size >= total) {
                                mAdapter.loadMoreEnd();
                                return;
                            }
                            if (swipeRefreshLayout.isRefreshing()) {
                                mAdapter.replaceData(response.getData().datas);
                            } else {
                                mAdapter.addData(response.getData().datas);
                            }
                            mAdapter.loadMoreComplete();
                            mAdapter.setEnableLoadMore(true);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            toast(response.getErrorMsg());
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFail(Throwable e) {
                        swipeRefreshLayout.setRefreshing(false);
                        mAdapter.setEnableLoadMore(false);
                        mAdapter.loadMoreFail();
                        Log.e(TAG, e.getMessage());
                    }
                });
    }

    public void showBannerData(List<BannerData> bannerDataList) {

        List<String> bannerImageList = new ArrayList<>();
        List<String> mBannerTitleList = new ArrayList<>();
        for (BannerData data : bannerDataList) {
            bannerImageList.add(data.imagePath);
            mBannerTitleList.add(data.title);
        }
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(bannerImageList);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(mBannerTitleList);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(bannerDataList.size() * 400);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);

        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }
}
