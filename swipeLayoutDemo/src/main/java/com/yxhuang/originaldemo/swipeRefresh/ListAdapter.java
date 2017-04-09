package com.yxhuang.originaldemo.swipeRefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yxhuang.originaldemo.R;

import java.util.List;

/**
 * Author  :  yxhuang
 * Date :  2017/4/4
 * Email : yxhuang1012@gmail.com
 */

public class ListAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mDates;
    private ViewHolder mViewHolder;

    public ListAdapter(Context context, List<String> datas) {
        mContext = context;
        update(datas);
    }

    public void update(List<String> list){
        mDates = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDates == null ? 0 : mDates.size();
    }

    @Override
    public Object getItem(int position) {
        return mDates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
            mViewHolder.tv_item = (TextView) convertView.findViewById(R.id.tv_item);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.tv_item.setText("这是第 " + mDates.get(position) + " 数据");

        return convertView;
    }


    static class  ViewHolder{
        TextView tv_item;
    }
}
