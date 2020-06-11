package com.kulloveth.newsfeed.ui.headlines;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kulloveth.newsfeed.AppUtils;
import com.kulloveth.newsfeed.R;
import com.kulloveth.newsfeed.databinding.FragmentHeadlineBinding;
import com.kulloveth.newsfeed.remote.ApiUtil;
import com.kulloveth.newsfeed.remote.model.Article;

public class HeadlineFragment extends Fragment {

    private static final String TAG = HeadlineFragment.class.getSimpleName();

    HeadlineViewModel viewModel;
    FragmentHeadlineBinding binding;
    RecyclerView recyclerView;
    HeadlineAdapter adapter;

    public HeadlineFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHeadlineBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toolbar toolbar = binding.appBar.toolbar;
        adapter = new HeadlineAdapter();
        recyclerView = binding.headlineRv;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        AppUtils.setToolbarTitle(getString(R.string.headline_fragment_category), ((AppCompatActivity) requireActivity()));
        viewModel = new ViewModelProvider(this).get(HeadlineViewModel.class);

        setUpHeadLineArticle();

    }


    private void setUpHeadLineArticle(){
        viewModel.getTopHeadlineByCountry("us", ApiUtil.API_KEY).observe(requireActivity(), articles -> {
            for (Article article: articles) {
                Log.d(TAG, "onActivityCreated: headlines by country " + article.getTitle());
            }
            adapter.submitList(articles);
        });
    }
}
