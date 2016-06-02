package com.tyrese.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tyrese.library.BaseItem;
import com.tyrese.library.BaseRecyclerViewAdapter;

/**
 * Created by Tyrese on 2016/5/18.
 */
public class MyAdapter extends BaseRecyclerViewAdapter {

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    public MyAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(android.R.layout.simple_list_item_1, null);
        ItemViewHolder vh = new ItemViewHolder(view);
        return vh;
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder holder, BaseItem item, int position) {
        ((ItemViewHolder) holder).textView.setText(((ItemMyData) item).getData());
    }
}
