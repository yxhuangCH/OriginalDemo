package com.yxhuang.retrofitdemo.network.base;

/**
 * Author  :  yxhuang
 * Date :  2016/10/25
 * Email : yxhuang1012@gmail.com
 */

import android.support.annotation.NonNull;

/**
 * 网络请求返回的异常
 */
public class ApiException extends RuntimeException {

    public static final int USER_NOT_EXIST = 0;
    public static final int WRONG_PASSWORD = 2;

    public ApiException(@NonNull int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(@NonNull String detailMessage) {
        super(detailMessage);
    }

    private static String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {
            case USER_NOT_EXIST:
                message = "该用户不存在";
                break;

            case WRONG_PASSWORD:
                message = "密码错误";
                break;

            default:
                message = "未知错误";
                break;
        }
        return message;
    }

}
