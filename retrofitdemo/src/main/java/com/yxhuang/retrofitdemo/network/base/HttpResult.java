package com.yxhuang.retrofitdemo.network.base;

/**
 * Author  :  yxhuang
 * Date :  2016/10/25
 * Email : yxhuang1012@gmail.com
 */

/**
 *  统一处理返回数据
 */
public class HttpResult<T> {

    private int code;

    private String msg;

    private T data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
