package com.yxhuang.retrofitdemo.ui;

import com.yxhuang.retrofitdemo.network.base.RetrofitClient;

import rx.Observable;

/**
 * Author  :  yxhuang
 * Date :  2017/4/8
 * Email : yxhuang1012@gmail.com
 */

public class MainRepository {

    public Observable<String> getContent(){
        return RetrofitClient.getInstance().getContent();
    }
}
