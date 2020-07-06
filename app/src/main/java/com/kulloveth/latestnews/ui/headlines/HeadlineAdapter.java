package com.kulloveth.latestnews.ui.headlines;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kulloveth.latestnews.utils.AppUtils;
import com.kulloveth.latestnews.R;
import com.kulloveth.latestnews.databinding.HeadlineListItemBinding;
import com.kulloveth.latestnews.remote.model.Article;
import com.squareup.picasso.Picasso;

public class HeadlineAdapter extends ListAdapter<Article, HeadlineAdapter.HeadLineViewHolder> {

    HeadlineListItemBinding binding;
    Activity activity;
    ItemCLickedListener clickedListener;
    ScrollDirection scrollDirection = ScrollDirection.DOWN;

    public HeadlineAdapter(Activity activity) {
        super(diffUtilCallback);
        this.activity = activity;
    }

    public void setClickedListener(ItemCLickedListener clickedListener) {
        this.clickedListener = clickedListener;
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
        private ImageView headlineImage, share, like;

        public HeadLineViewHolder(HeadlineListItemBinding binding) {
            super(binding.getRoot());
            titleTv = binding.title;
            descriptionTv = binding.description;
            headlineImage = binding.articleImage;
            like = binding.like;
            share = binding.share;
        }

        private void bind(Article article) {
            titleTv.setText(article.getTitle());
            descriptionTv.setText(article.getDescription());
            String path = article.getUrlToImage();
            if (path != null) {
                if (path.isEmpty())
                    path = null;
                Picasso.get().load(path).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(headlineImage);


            } else {
                headlineImage.setImageResource(R.drawable.ic_launcher_background);
            }
            share.setOnClickListener(v -> {
                AppUtils.shareNewsTitle(v.getContext(), activity, article.getTitle() + "\n" + article.getUrl());
            });
            like.setOnClickListener(v -> clickedListener.itemClicked(article, getAdapterPosition()));
            animateVie(binding.getRoot());
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

    private void animateVie(View viewToAnimaate) {
        if (viewToAnimaate.getAnimation() == null) {
            int animId;
            if (scrollDirection == ScrollDirection.DOWN) {
                animId = R.anim.slide_from_botttom;
            } else {
                animId = R.anim.slide_from_top;
            }
            Animation animation = AnimationUtils.loadAnimation(viewToAnimaate.getContext(), animId);
            viewToAnimaate.setAnimation(animation);
        }
    }

    public interface ItemCLickedListener {
        void itemClicked(Article article, int position);
    }

    public enum ScrollDirection {
        TOP, DOWN
    }
}
