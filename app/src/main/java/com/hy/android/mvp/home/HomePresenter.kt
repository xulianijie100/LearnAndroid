package com.hy.android.mvp.home

import android.util.Log
import com.hy.android.base.BasePresenter
import com.hy.android.mvp.entity.BannerEntity
import com.hy.android.mvp.entity.HomeEntity
import com.hy.android.net.BaseObserver
import com.hy.android.net.RetrofitHelper
import com.hy.android.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {
    private val tag = "HomePresenter"
    override fun getBanner() {
        RetrofitHelper.getInstance().getApiService(Constants.ANDROID_URL).bannerList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<BannerEntity>() {
                    override fun onSuccess(bean: BannerEntity) {
                        if (bean.errorCode == 0) {
                            mView?.setBanner(bean)
                        }
                    }
                    override fun onFail(e: Throwable) {
                        Log.e(tag, e.message)
                    }
                })
    }

    override fun getHomeList(page: Int) {
        RetrofitHelper.getInstance().getApiService(Constants.ANDROID_URL).getHomePageList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<HomeEntity>() {
                    override fun onSuccess(bean: HomeEntity) {
                        if (bean.errorCode == 0) {
                            mView?.setHomeList(bean)
                        }
                    }
                    override fun onFail(e: Throwable) {
                        Log.e(tag, e.message)
                    }
                })
    }
}