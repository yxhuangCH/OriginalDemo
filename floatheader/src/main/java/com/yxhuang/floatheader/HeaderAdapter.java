package com.yxhuang.floatheader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Author  :  yxhuang
 * Date :  2017/5/21
 * Email : yxhuang1012@gmail.com
 */

public class HeaderAdapter  extends RecyclerView.Adapter<HeaderAdapter.ViewHolder>{

    private List<ItemBean> mList;
    private Context mContext;

    public static final int HAS_STICKY_VIEW = 1;
    public static final int NONE_STICKY_VIEW = 2;

    public HeaderAdapter(Context context, List<ItemBean> list) {
        mContext = context;
       update(list);
    }

    public void update(List<ItemBean> list){
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemBean itemBean = mList.get(position);

        holder.tv_time.setText(itemBean.getTime());
        holder.tv_content.setText(itemBean.getContent());
        holder.tv_header.setText(itemBean.getTime());

        if (position == 0){
            holder.header_view.setVisibility(View.VISIBLE);
            holder.tv_header.setText(itemBean.getTime());
        } else {
            String lastTime = mList.get(position - 1).getTime();
            if (!TextUtils.equals(itemBean.getTime(), lastTime)){
                holder.header_view.setVisibility(View.VISIBLE);
                holder.itemView.setTag(HAS_STICKY_VIEW);
            } else {
                holder.header_view.setVisibility(View.GONE);
                holder.itemView.setTag(NONE_STICKY_VIEW);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout header_view;
        TextView tv_header;

        TextView tv_time;
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);

            header_view = (LinearLayout) itemView.findViewById(R.id.header_view);
            tv_header = (TextView) itemView.findViewById(R.id.tv_header);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
