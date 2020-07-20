package com.hy.android.net;

import com.hy.android.base.BaseApplication;
import com.hy.android.net.cookies.CookiesManager;
import com.hy.android.utils.CommonUtils;
import com.hy.android.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private ApiService apiService;
    private static final int DEFAULT_TIMEOUT = 20;
    private static RetrofitHelper instance = null;

    private Retrofit.Builder builder;

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null)
                    instance = new RetrofitHelper();
            }
        }
        return instance;
    }

    private RetrofitHelper() {
        builder = new Retrofit.Builder();
    }


    private OkHttpClient getClient() {
        String PATH_DATA = BaseApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
        String PATH_CACHE = PATH_DATA + "/NetCache";
        File cacheFile = new File(PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addNetworkInterceptor(getInterceptor());
        builder.addInterceptor(getInterceptor());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        //cookie认证
        builder.cookieJar(new CookiesManager());

        return builder.build();
    }

    private Interceptor getInterceptor() {
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!CommonUtils.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (CommonUtils.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        return cacheInterceptor;
    }

    public ApiService getApiService(String url) {
        builder.baseUrl(url)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = builder.build().create(ApiService.class);
        return apiService;
    }

    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Logger.d("data ==", message);
        }
    });


}
