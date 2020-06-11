package com.kulloveth.newsfeed.ui.category.sports;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kulloveth.newsfeed.R;
import com.kulloveth.newsfeed.databinding.FragmentSportsBinding;
import com.kulloveth.newsfeed.remote.ApiUtil;
import com.kulloveth.newsfeed.remote.model.Article;
import com.kulloveth.newsfeed.ui.category.category.CategoryViewModel;
import com.kulloveth.newsfeed.ui.category.entertainment.EntertainmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SportsFragment extends Fragment {

    private static final String TAG = SportsFragment.class.getSimpleName();

    FragmentSportsBinding binding;
    CategoryViewModel viewModel;
    RecyclerView recyclerView;
    SportsAdapter adapter;

    public SportsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSportsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = binding.sportsRv;
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        adapter = new SportsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);
        setUpSportsArticle();
    }

    private void setUpSportsArticle() {
        viewModel.getTechnologyCategory("sports", ApiUtil.API_KEY).observe(requireActivity(), articles -> {
            for (Article article : articles) {
                Log.d(TAG, "onActivityCreated: headlines by country " + article.getTitle());
            }
            adapter.submitList(articles);
        });
    }
}
