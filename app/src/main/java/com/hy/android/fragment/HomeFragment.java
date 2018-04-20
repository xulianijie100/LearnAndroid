package com.hy.android.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import com.hy.android.Base.BaseFragment;
import com.hy.android.R;
import com.hy.android.adapter.BannerAdapter;
import com.hy.android.adapter.HomeAdapter;
import com.hy.android.bean.BannerData;
import com.hy.android.bean.BaseResponse;
import com.hy.android.bean.HomeData;
import com.hy.android.net.RetrofitHelper;
import com.hy.android.view.HorizontalRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/17.
 */

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private HorizontalRecyclerView bannerRecyclerView;
    private BannerAdapter bannerAdapter;
    private HomeAdapter mAdapter;
    private List<HomeData> homeDatas;
    private List<BannerData> bannerDatas;
    private int currentIndex = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        homeDatas = new ArrayList<>();
        bannerDatas = new ArrayList<>();

        swipeRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout);

        //banner
        bannerRecyclerView = (HorizontalRecyclerView) LayoutInflater.from(getActivity()).inflate(R.layout.home_banner, null);
        bannerAdapter = new BannerAdapter(getActivity(),R.layout.banner_item, bannerDatas);
        bannerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        new PagerSnapHelper().attachToRecyclerView(mRecyclerView); //一次只能滑一页，而且居中显示。
        bannerRecyclerView.requestDisallowInterceptTouchEvent(true);
        bannerRecyclerView.setAdapter(bannerAdapter);
        //list
        mRecyclerView = mView.findViewById(R.id.recyclerView);
        mAdapter = new HomeAdapter(R.layout.home_list_item, homeDatas);
        mAdapter.addHeaderView(bannerRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void refreshData() {
        swipeRefreshLayout.setRefreshing(true);
        //mAdapter.setEnableLoadMore(false);
        setPlaying(false);
        getBanner();
        getHomeList();
    }

    private void getBanner() {
        RetrofitHelper.getInstance().getApiService().getBannerData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<BannerData>>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<BannerData>> response) {
                        if (response != null && response.getData().size() > 0) {
                            bannerDatas.clear();
                            bannerDatas.addAll(response.getData());
                        }
                        Log.e(TAG, "onNext: "+bannerDatas.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        swipeRefreshLayout.setRefreshing(false);
                        bannerAdapter.notifyDataSetChanged();
                        setPlaying(true);
                    }
                });
    }

    private void getHomeList() {

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            bannerRecyclerView.smoothScrollToPosition(++currentIndex);
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    };

    public void setPlaying(boolean playing) {
        if (playing && bannerAdapter.getItemCount() > 2) {
            handler.sendEmptyMessageDelayed(0, 3000);
        } else if (!playing) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
