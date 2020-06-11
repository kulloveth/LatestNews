package com.kulloveth.newsfeed.ui.headlines;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kulloveth.newsfeed.AppUtils;
import com.kulloveth.newsfeed.R;
import com.kulloveth.newsfeed.databinding.HeadlineListItemBinding;
import com.kulloveth.newsfeed.remote.model.Article;
import com.squareup.picasso.Picasso;

public class HeadlineAdapter extends ListAdapter<Article, HeadlineAdapter.HeadLineViewHolder> {

    HeadlineListItemBinding binding;
    Activity activity;

    public HeadlineAdapter(Activity activity) {
        super(diffUtilCallback);
        this.activity = activity;
    }


    @NonNull
    @Override
    public HeadLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = HeadlineListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HeadLineViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadLineViewHolder holder, int position) {
        Article article = getItem(position);
        holder.bind(article);
    }

    class HeadLineViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTv;
        private TextView descriptionTv;
        private ImageView headlineImage, share;

        public HeadLineViewHolder(HeadlineListItemBinding binding) {
            super(binding.getRoot());
            titleTv = binding.title;
            descriptionTv = binding.description;
            headlineImage = binding.articleImage;
            share = binding.share;
        }

        private void bind(Article article) {
            titleTv.setText(article.getTitle());
            descriptionTv.setText(article.getDescription());
            String path = article.getUrlToImage();
            Picasso.get().load(path).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(headlineImage);
            share.setOnClickListener(v -> {
                AppUtils.shareNewsTitle(v.getContext(), activity, article.getTitle());
            });
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

    public interface ItemCLickedListener {
        void itemClicked(Article article);
    }
}
