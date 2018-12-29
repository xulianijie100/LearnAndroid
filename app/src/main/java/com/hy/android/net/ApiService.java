package com.hy.android.net;

import com.hy.android.bean.BannerData;
import com.hy.android.bean.BaseResponse;
import com.hy.android.bean.HomeDataList;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

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


    /**
     * http://www.wanandroid.com/article/list/0/json
     *
     * @return 首页数据
     */
    @GET("/article/list/{page}/json")
    Observable<BaseResponse<HomeDataList>> getHomeList(@Path("page") int page);
}
