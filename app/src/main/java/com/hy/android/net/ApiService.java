package com.hy.android.net;

import com.hy.android.bean.BannerData;
import com.hy.android.bean.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2018/4/17.
 */

public interface ApiService {

    String HOST = "http://www.wanandroid.com/";

    /**
     * 广告栏
     * http://www.wanandroid.com/banner/json
     *
     * @return 广告栏数据
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerData>>> getBannerData();
}
