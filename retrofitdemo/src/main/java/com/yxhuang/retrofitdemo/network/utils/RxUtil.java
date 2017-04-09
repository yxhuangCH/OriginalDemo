package com.yxhuang.retrofitdemo.network.utils;


import com.yxhuang.retrofitdemo.network.base.ApiException;
import com.yxhuang.retrofitdemo.network.base.HttpResult;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author  :  yxhuang
 * Date :  2016/11/27
 * Email : yxhuang1012@gmail.com
 */

public class RxUtil {


    /**
     *  统一线程处理
     * @param <T>
     * @return
     */
    public static  <T>Observable.Transformer<T, T> ioToMainThread(){
        return new Observable.Transformer<T, T>(){
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     *  统一返回结果处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<HttpResult<T>, T> handleResult(){
        return new Observable.Transformer<HttpResult<T>, T>(){
            @Override
            public Observable<T> call(Observable<HttpResult<T>> httpResultObservable) {
                return httpResultObservable.flatMap(new Func1<HttpResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(HttpResult<T> tHttpResult) {
                        int statusCode = tHttpResult.getCode();
                        if (statusCode == NetworkUtils.STATUS_SUCCESS){
                            return createData(tHttpResult.getData());
                        } else {
                            return Observable.error(new ApiException(tHttpResult.getMsg()));
                        }
                    }
                });
            }
        };
    }


    /**
     *  生成 Observable
     * @param t
     * @param <T>
     * @return
     */
    public static  <T> Observable<T> createData(final T t){
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e){
                    subscriber.onError(e);
                    e.printStackTrace();
                }
            }
        });
    }

    public static Observable<String> creaDate(final String data){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }
            }
        });
    }

}
