package com.yxhuang.retrofitdemo.ui;

import rx.subscriptions.CompositeSubscription;

/**
 * Author  :  yxhuang
 * Date :  2017/4/8
 * Email : yxhuang1012@gmail.com
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private CompositeSubscription mSubscription;

    public MainPresenter(MainContract.View view) {
        mView = view;

        mSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {



    }

    @Override
    public void unsubscribe() {
        mSubscription.clear();
    }
}
