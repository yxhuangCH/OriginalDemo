package com.yxhuang.retrofitdemo.app;

import android.app.Application;

/**
 * Author  :  yxhuang
 * Date :  2017/4/8
 * Email : yxhuang1012@gmail.com
 */

public class LokApp extends Application {

    private static LokApp sInstance;

    public static synchronized LokApp getInstance(){
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

}
