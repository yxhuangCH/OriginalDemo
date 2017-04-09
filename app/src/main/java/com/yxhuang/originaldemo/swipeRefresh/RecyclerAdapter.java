package com.yxhuang.originaldemo.swipeRefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yxhuang.originaldemo.R;

import java.util.List;

/**
 * Author  :  yxhuang
 * Date :  2017/4/4
 * Email : yxhuang1012@gmail.com
 */

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.VHolder>{

    private Context mContext;
    private List<String> mList;

    public RecyclerAdapter(Context context, List<String> list) {
        mContext = context;
        update(list);
    }

    public void update(List<String> list){
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(VHolder holder, int position) {
        holder.item.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static  class VHolder extends RecyclerView.ViewHolder{
        TextView item;

        public VHolder(View itemView) {
            super(itemView);

            item = (TextView) itemView.findViewById(R.id.tv_item);

        }
    }
}
