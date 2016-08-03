package com.tyrese.test;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tyrese.library.BaseItem;
import com.tyrese.library.BaseRecyclerViewAdapter;
import com.tyrese.library.EndlessRecyclerOnScrollListener;
import com.tyrese.library.ItemData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mListView;

    private static final int PAGE_SIZE = 10;

    private BaseRecyclerViewAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<BaseItem> mData = new ArrayList<>();
    private EndlessListener mScrollListener;
    private int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mListView = (RecyclerView) findViewById(R.id.list_view);

        layoutManager = new LinearLayoutManager(this);
        mScrollListener = new EndlessListener(layoutManager);
        mListView.setLayoutManager(layoutManager);
        String[] array = new String[]{"George", "Zubin", "Carlos", "Frank", "Charles"};
        ItemMyData item = null;
        for (String str : array) {
            item = new ItemMyData();
            item.setData(str);
            mData.add(item);
        }

        mScrollListener.setNeedLoadMore(mData.size() >= PAGE_SIZE);

        mAdapter = new MyAdapter(this);
        mAdapter.setNewData(mData, mScrollListener.isNeedLoadMore());
        mListView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List<BaseItem> ddd = new ArrayList<>();
                ItemMyData itemData = null;
                for (int i = 0; i < 10; i ++) {
                    itemData = new ItemMyData();
                    itemData.setData("Test " + i);
                    ddd.add(itemData);
                }

                boolean isMore = ddd.size() >= PAGE_SIZE;
                mScrollListener.setNeedLoadMore(isMore);
                mScrollListener.resetStatus();
                mAdapter.setNewData(ddd, isMore);

                //如果没有结果的话
                //mAdapter.removeFooter();

                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
                num = 1;
            }
        });

        mListView.addOnScrollListener(mScrollListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadMore(final int currentPage) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<BaseItem> datas = new ArrayList<BaseItem>();
                ItemMyData itemData = null;
                int count = 10;
                if (num >= 3) {
                    count = 3;
                }
                for (int i = 0;i < count; i ++) {
                    itemData = new ItemMyData();
                    itemData.setData("Test : " + currentPage);
                    datas.add(itemData);
                }
                num ++;


                mScrollListener.setNeedLoadMore(datas.size() >= PAGE_SIZE);

                mAdapter.addItems(datas, mScrollListener.isNeedLoadMore());
            }
        }, 3000);
    }

    private class EndlessListener extends EndlessRecyclerOnScrollListener {

        public EndlessListener(LinearLayoutManager linearLayoutManager) {
            super(linearLayoutManager);
        }

        @Override
        public void onLoadMore(int currentPage) {
            loadMore(currentPage);
        }
    }
}
