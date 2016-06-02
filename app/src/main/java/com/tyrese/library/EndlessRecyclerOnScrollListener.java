package com.tyrese.library;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by Tyrese on 2016/5/11.
 */
public abstract class EndlessRecyclerOnScrollListener extends
        RecyclerView.OnScrollListener {

    private int previousTotal = 0;
    private boolean loading = true;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    protected boolean isNeedLoadMore = true;

    private int currentPage = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(
            LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy <= 0) {
            return;
        }

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading
                && (totalItemCount - visibleItemCount) <= firstVisibleItem) {
            currentPage++;
            if (isNeedLoadMore) {
                onLoadMore(currentPage);
            }
            loading = true;
        }
    }

    public boolean isNeedLoadMore() {
        return isNeedLoadMore;
    }

    public void setNeedLoadMore(boolean isMore) {
        isNeedLoadMore = isMore;
    }
    public void resetStatus() {
        previousTotal = 0;
        currentPage = 1;
    }

    public abstract void onLoadMore(int currentPage);
}
