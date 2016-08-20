package me.zhouzhuo.zzsecondarylinkagedemo.api;


import java.util.concurrent.TimeUnit;

import me.zhouzhuo.zzsecondarylinkagedemo.constants.Constants;
import me.zhouzhuo.zzsecondarylinkagedemo.utils.LogUtils;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by baixiaokang on 16/3/9.
 */
public class Api {

    public ApiService mApiService;

    HttpLoggingInterceptor mLog = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            LogUtils.api(message);
        }
    });

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final Api INSTANCE = new Api();
    }

    //获取单例
    public static Api getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //构造方法私有
    private Api() {
        mApiService = getRetrofit(Constants.BASE_URL).create(ApiService.class);
    }

    private Retrofit getRetrofit(String host) {
        return new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(host)
                .build();
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        mLog.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .readTimeout(3000, TimeUnit.MILLISECONDS)
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .addInterceptor(mLog)
                .build();
    }

    /**
     * download
     */
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <T> T createService(Class<T> tClass) {
        return builder.build().create(tClass);
    }

}