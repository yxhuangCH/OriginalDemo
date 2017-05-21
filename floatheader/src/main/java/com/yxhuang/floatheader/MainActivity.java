package com.yxhuang.floatheader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HeaderAdapter mHeaderAdapter;
    private List<ItemBean> mList = new ArrayList<>(0);
    private RecyclerView mRecyclerView;

    private LinearLayout mHeaderView;
    private TextView tv_header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mHeaderView = (LinearLayout) findViewById(R.id.header_view);
        tv_header = (TextView) findViewById(R.id.tv_header);

        final LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, manager.getOrientation()));
        mHeaderAdapter = new HeaderAdapter(this, mList);
        mRecyclerView.setAdapter(mHeaderAdapter);

        // 头部置顶
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View transInfoView = recyclerView.findChildViewUnder(
                        mHeaderView.getMeasuredWidth() /2,
                        mHeaderView.getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null){
                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - mHeaderView.getMeasuredHeight();

                    if (transViewStatus == mHeaderAdapter.HAS_STICKY_VIEW){
                        if (transInfoView.getTop() > 0){
                            mHeaderView.setTranslationY(dealtY);
                        } else {
                            mHeaderView.setTranslationY(0);
                        }

                        int position = manager.findFirstVisibleItemPosition();
                        updateFloatHeaderView(position);


                    } else if (transViewStatus == mHeaderAdapter.NONE_STICKY_VIEW){
                        mHeaderView.setTranslationY(0);
                    }
                }
            }
        });
        generateDate();

        updateFloatHeaderView(0);
    }


    private void updateFloatHeaderView(int position){
        tv_header.setText(mList.get(position).getTime());
    }



    private void generateDate(){
        String time1 = "20170519";
        for (int i = 0; i <= 5 ; i++){
            ItemBean itemBean = new ItemBean(time1, "内容 " + i);
            mList.add(itemBean);
        }

        String time2 = "20170520";
        for (int i = 0; i <= 5 ; i++){
            ItemBean itemBean = new ItemBean(time2, "内容 " + i + i);
            mList.add(itemBean);
        }

        String time3 = "20170521";
        for (int i = 0; i <= 5 ; i++){
            ItemBean itemBean = new ItemBean(time3, "内容 " + i + i + i);
            mList.add(itemBean);
        }

        String time4 = "20170522";
        for (int i = 0; i <= 5 ; i++){
            ItemBean itemBean = new ItemBean(time4, "内容 " + i + i + i + i);
            mList.add(itemBean);
        }

        mHeaderAdapter.update(mList);
    }
}
