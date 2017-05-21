package com.yxhuang.retrofitdemo.ui;

import android.util.Log;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Author  :  yxhuang
 * Date :  2017/4/8
 * Email : yxhuang1012@gmail.com
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private CompositeSubscription mSubscription;
    private MainRepository mRepository;

    public MainPresenter(MainContract.View view) {
        mView = view;

        mSubscription = new CompositeSubscription();
        mRepository = new MainRepository();
    }

    @Override
    public void subscribe() {

        Subscription subscribe = mRepository.getContent()
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showNetworkResult(e.getMessage());
                        Log.e("Main", "error " + e.getMessage());

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("Main", "resulte  "  + s);

                    }
                });
        mSubscription.add(subscribe);

    }

    @Override
    public void unsubscribe() {
        mSubscription.clear();
    }

}
