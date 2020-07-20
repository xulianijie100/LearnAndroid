package com.hy.android.mvp.home

import com.hy.android.base.BaseContract
import com.hy.android.mvp.entity.BannerEntity
import com.hy.android.mvp.entity.HomeEntity

interface HomeContract {

    interface View : BaseContract.BaseView {
        fun setBanner(bean: BannerEntity)
        fun setHomeList(bean: HomeEntity)
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun getBanner()
        fun getHomeList(page:Int)
    }

}