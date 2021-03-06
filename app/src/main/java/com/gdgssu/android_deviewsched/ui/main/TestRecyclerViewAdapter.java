package com.gdgssu.android_deviewsched.ui.main;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdgssu.android_deviewsched.R;

import java.util.List;

public class TestRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> mContents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public TestRecyclerViewAdapter(List<Object> contents) {
        this.mContents = contents;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_list_card_big, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_list_card_small, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_CELL:
                break;
        }
    }
}