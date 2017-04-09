package com.yxhuang.retrofitdemo.network.base;

/**
 * Author: yxhuang
 * Date: 2016/10/24
 * Email: yxhuang@gmail.com
 */


import android.content.Context;

import com.yxhuang.retrofitdemo.app.AppConfig;
import com.yxhuang.retrofitdemo.app.LokApp;
import com.yxhuang.retrofitdemo.network.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  网络请求
 */
public class RetrofitClient {


    // path
    public static final String PATH_DATA = LokApp.getInstance().getCacheDir()
            .getAbsolutePath() + File.separator + "data";
    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    private static final int FILE_CACHE = 1024 * 1024 * 50;
    private static final int DEFAULT_CONNECT_TIMEOUT = 15;
    private static final int DEFAULT_READ_TIMEOUT = 20;
    private static final int DEFAULT_WRITE_TIMEOUT = 20;


    private static OkHttpClient sOkHttpClient;

    public static RetrofitClient getInstance(){
        return SingletonHolder.INSTANCE;
    }

    // 内部静态类单例模式
    private static class SingletonHolder{
        private static final RetrofitClient INSTANCE = new RetrofitClient();
    }

    private RetrofitClient(){
        init();
    }

    private void init(){
        initOkHttp();
        initService();
    }

    private static void initOkHttp(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if(AppConfig.DEBUG){
            logConfig(builder);
        }

        // 配置缓存
        cacheConfig(builder);
        //设置超时
        builder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        // 配置公共参数
        setCommonParam(builder);
        // 设置 Cookie
        setCookie(builder);

        sOkHttpClient = builder.build();
    }

    private static void initService(){

    }

    // log 配置
    private static void logConfig(OkHttpClient.Builder builder){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
    }

    // 配置缓存
    private static void cacheConfig(OkHttpClient.Builder builder){
        File cacheFile = new File(PATH_CACHE);
        Cache cache = new Cache(cacheFile, FILE_CACHE);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtils.isNetworkConnected()) {
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

        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
    }

    // 设置 Cookie
    private static void setCookie(OkHttpClient.Builder builder){
        Context context = LokApp.getInstance().getApplicationContext();
        builder.addInterceptor(new AddCookiesInterceptor(context));
        builder.addInterceptor(new SaveCookiesInterceptor(context));
    }


    private static <T>  T getService(String baseUrl, Class<T> classzz){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(sOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(classzz);

    }

    private static <T>  T getService(Class<T> classzz){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.BASE_URL)
                .client(sOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(classzz);

    }

    /**
     * 配置公共参数
     * @param builder
     */
    private static void setCommonParam(OkHttpClient.Builder builder){
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
//                        .addQueryParameter()  //
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };

         //公共参数
        builder.addInterceptor(addQueryParameterInterceptor);

    }

}
