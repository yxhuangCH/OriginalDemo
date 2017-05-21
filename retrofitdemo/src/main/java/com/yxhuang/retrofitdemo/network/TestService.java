package com.yxhuang.retrofitdemo.network;

import com.yxhuang.retrofitdemo.network.base.HttpResult;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Author  :  yxhuang
 * Date :  2017/4/25
 * Email : yxhuang1012@gmail.com
 */

public interface TestService {

    @GET("servlet/GlobalTrip")
    Observable<HttpResult<String>> getContent();
}
