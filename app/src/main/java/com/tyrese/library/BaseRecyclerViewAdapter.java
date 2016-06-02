package com.tyrese.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


import com.tyrese.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyrese on 2016/5/11.
 */
public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_ITEM = 1;

    protected Context mCtx;
    protected ArrayList<BaseItem> mData = new ArrayList<>();
    protected LayoutInflater mInflater;
    private boolean isLoading = true;

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        Button btnLoadMore;
        LinearLayout loadingLayout;

        public FooterViewHolder(View itemView) {
            super(itemView);
            btnLoadMore = (Button) itemView.findViewById(R.id.btn_load_more);
            loadingLayout = (LinearLayout) itemView.findViewById(R.id.load_more_view);
        }
    }

    public BaseRecyclerViewAdapter(Context context) {
        this.mCtx = context;
        this.mInflater = LayoutInflater.from(this.mCtx);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (TYPE_ITEM == viewType) {
            vh = onCreateMyViewHolder(parent);
        } else if (TYPE_FOOTER == viewType) {
            View view = mInflater.inflate(R.layout.custom_list_footer, null);
            vh = new FooterViewHolder(view);
        }
        return vh;
    }

    public abstract RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof FooterViewHolder) {
                if (isLoading) {
                    ((FooterViewHolder) holder).btnLoadMore.setVisibility(View.GONE);
                    ((FooterViewHolder) holder).loadingLayout.setVisibility(View.VISIBLE);
                } else {
                    //这里没有使用，暂时留着可能用作点击加载。
                    ((FooterViewHolder) holder).btnLoadMore.setVisibility(View.VISIBLE);
                    ((FooterViewHolder) holder).loadingLayout.setVisibility(View.GONE);
                    ((FooterViewHolder) holder).btnLoadMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            } else {
                onBindMyViewHolder(holder, mData.get(position), position);
            }
    }

    public abstract void onBindMyViewHolder(RecyclerView.ViewHolder holder, BaseItem item, int position);

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        BaseItem item = mData.get(position);
        return item.getCustomItemType();
    }

    public void addItem(BaseItem item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addItems(List<BaseItem> moreData, boolean isNeedMore) {
        mData.remove(mData.size() - 1);
        mData.addAll(moreData);
        if (isNeedMore) {
            mData.add(new ItemFooter());
        }
        notifyDataSetChanged();
    }

    public void setNewData(List<BaseItem> newData, boolean isNeedMore) {
        mData.clear();
        mData.addAll(newData);
        if (isNeedMore) {
            ItemFooter footer = new ItemFooter();
            mData.add(footer);
        }
        notifyDataSetChanged();
    }

    public void delete(int index) {
        mData.remove(index);
        notifyDataSetChanged();
    }

    public void delete(BaseItem item) {
        mData.remove(item);
        notifyDataSetChanged();
    }

    public void removeFooter() {
        if (mData.size() > 0) {
            mData.remove(mData.size() - 1);
            notifyDataSetChanged();
        }
    }

}
