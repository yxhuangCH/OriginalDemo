package com.yxhuang.retrofitdemo.ui;

import com.yxhuang.retrofitdemo.ui.base.BasePresenter;
import com.yxhuang.retrofitdemo.ui.base.BaseView;

/**
 * Author  :  yxhuang
 * Date :  2017/4/8
 * Email : yxhuang1012@gmail.com
 */

public interface MainContract {

    interface  Presenter extends BasePresenter{


    }

    interface View extends BaseView{

        void showNetworkResult(String result);
    }


}
