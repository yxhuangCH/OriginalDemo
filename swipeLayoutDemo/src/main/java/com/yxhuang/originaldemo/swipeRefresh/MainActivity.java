package com.yxhuang.originaldemo.swipeRefresh;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yxhuang.originaldemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author  :  yxhuang
 * Date :  2017/4/4
 * Email : yxhuang1012@gmail.com
 */

/**
 *  SwipeLayout Demo
 */
public class MainActivity extends AppCompatActivity {

    private static final int  FINISH_REFRESH = 1;

    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private ListView mListView;

    private ListAdapter mListAdapter;
    private List<String> mList = new ArrayList<>(0);

    private RecyclerAdapter  mRecyclerAdapter;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == FINISH_REFRESH){
                 if (mSwipeLayout.isRefreshing()){
                     mSwipeLayout.setRefreshing(false);
                     Log.i("yxh", "handle message");
                     updateListView();
                 }
            }
        }
    };

    private DividerItemDecoration mDividerItemDecoration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDividerItemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL){
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(50,50, 50, 50);
            }
        };

        initView();
    }

    private void initView(){
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerAdapter = new RecyclerAdapter(this, mList);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mSwipeLayout.setNestedScrollingEnabled(true);
//        mListView = (ListView) findViewById(R.id.listView);

//        mListAdapter  = new ListAdapter(this, mList);
//        mListView.setAdapter(mListAdapter);

        //设置下拉刷新的监听
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addDate();
            }
        });
    }

    private void addDate(){
        for (int  i = 0; i < 10; i++){
            mList.add("" + i);
        }
        mHandler.sendEmptyMessage(FINISH_REFRESH);
    }

    private void updateListView(){
//        mListAdapter.update(mList);
        mRecyclerAdapter.update(mList);
    }



}