package com.paulniu.ying;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.niupuyue.mylibrary.utils.NetStatusUtility;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 18:47
 * Desc:
 * Version:
 */
public class RetorfitFactory {

    private volatile static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (null == retrofit) {
            synchronized (RetorfitFactory.class) {
                if (null == retrofit) {
                    // 指定缓存路径
                    Cache cache = new Cache(new File(App.getAppContext().getCacheDir(), "httpCache"), 1024 * 1024 * 50);
                    // cookie 持久化
                    ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getAppContext()));

                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .cookieJar(cookieJar)
                            .cache(cache)
                            .addInterceptor(cacheControllInterceptor)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true);

                    retrofit = new Retrofit.Builder()
                            .baseUrl(ApiService.baseUrl)
                            .client(builder.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    private static final Interceptor cacheControllInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetStatusUtility.isNetAvailable(App.getAppContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originResponse = chain.proceed(request);
            if (NetStatusUtility.isNetAvailable(App.getAppContext())) {
                // 有网络时，设置缓存为默认值
                String cacheControll = request.cacheControl().toString();
                return originResponse.newBuilder()
                        .header("Cache-Control", cacheControll)
                        .removeHeader("prama")
                        .build();
            } else {
                // 无网络时 设置超时为1周
                int maxStale = 60 * 60 * 24 * 7;
                return originResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

}
