package com.yxhuang.floatheader;

/**
 * Author  :  yxhuang
 * Date :  2017/5/21
 * Email : yxhuang1012@gmail.com
 */

public class ItemBean {

    private String mTime;
    private String mContent;

    public ItemBean(){

    }

    public ItemBean(String time, String content) {
        mTime = time;
        mContent = content;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
