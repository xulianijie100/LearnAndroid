package com.hy.android.module;

import com.hy.android.base.BaseApplication;
import com.hy.android.net.RetrofitConfig;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Module
public class HttpModule {


    @Provides
    OkHttpClient.Builder provideOkHttpClient() {
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(BaseApplication.getInstance().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        return new OkHttpClient().newBuilder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(RetrofitConfig.sLoggingInterceptor)
                .addInterceptor(RetrofitConfig.sRewriteCacheControlInterceptor)
                .addNetworkInterceptor(RetrofitConfig.sRewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS);
    }

}
