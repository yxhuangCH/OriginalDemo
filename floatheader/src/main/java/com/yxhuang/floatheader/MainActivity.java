package com.yxhuang.floatheader;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int FINISH_REFRESH = 1000;

    private HeaderAdapter mHeaderAdapter;
    private List<ItemBean> mList = new ArrayList<>(0);
    private RecyclerView mRecyclerView;

    private LinearLayout mHeaderView;
    private TextView tv_header;
    private SwipeRefreshLayout swipe_layout;

    private EndlessRecyclerOnScrollListener mEndlessRecyclerOnScrollListener;
    private View mFooterView;
    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == FINISH_REFRESH){
                swipe_layout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipe_layout.setRefreshing(false);
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mHeaderView = (LinearLayout) findViewById(R.id.header_view);
        tv_header = (TextView) findViewById(R.id.tv_header);
        swipe_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);

        final LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, manager.getOrientation()));
        mHeaderAdapter = new HeaderAdapter(this, mList);

        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mHeaderAdapter);
        mFooterView = LayoutInflater.from(this).inflate(R.layout.view_footer, mRecyclerView, false);
        mHeaderViewRecyclerAdapter.addFooterView(mFooterView);
        mFooterView.setVisibility(View.GONE);

        mRecyclerView.setAdapter(mHeaderViewRecyclerAdapter);

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

        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(FINISH_REFRESH, 3 * 1000);

                // 1. 重置
                mEndlessRecyclerOnScrollListener.reset();
                // 2. 请求网络数据第一页
                // TODO: 2017/5/21  网络请求第一页数据
            }
        });

        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int currentPage) {
                mFooterView.setVisibility(View.VISIBLE);

                // TODO: 2017/5/21  请求数据
                // 加载更多， 传 currentPage  进去

                //弱网络情况下，数据不回来，则在 6后消失 footerView
                mFooterView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mFooterView.getVisibility() == View.VISIBLE){
                            mFooterView.setVisibility(View.GONE);
                        }
                    }
                }, 6 * 1000);
            }
        });
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
