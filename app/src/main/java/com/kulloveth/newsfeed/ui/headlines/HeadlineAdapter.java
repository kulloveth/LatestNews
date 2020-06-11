package com.kulloveth.newsfeed.ui.headlines;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kulloveth.newsfeed.remote.model.Article;

public class HeadlineAdapter extends ListAdapter<Article, HeadlineAdapter.HeadLineViewHolder> {

    public HeadlineAdapter() {
        super(diffUtilCallback);
    }

    @NonNull
    @Override
    public HeadLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HeadLineViewHolder holder, int position) {

    }

    class HeadLineViewHolder extends RecyclerView.ViewHolder {
        public HeadLineViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static DiffUtil.ItemCallback<Article> diffUtilCallback = new DiffUtil.ItemCallback<Article>() {
        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.equals(newItem);
        }
    };
}
