package com.kulloveth.newsfeed.ui.category.entertainment;

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

import com.google.android.material.snackbar.Snackbar;
import com.kulloveth.newsfeed.R;
import com.kulloveth.newsfeed.databinding.FragmentEntertainmentBinding;
import com.kulloveth.newsfeed.remote.ApiUtil;
import com.kulloveth.newsfeed.remote.model.Article;
import com.kulloveth.newsfeed.ui.category.category.CategoryViewModel;
import com.kulloveth.newsfeed.utils.AppUtils;
import com.kulloveth.newsfeed.utils.ProgressListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntertainmentFragment extends Fragment implements ProgressListener {

    private static final String TAG = EntertainmentFragment.class.getSimpleName();

    FragmentEntertainmentBinding binding;
    EntertainmentAdapter adapter;
    RecyclerView recyclerView;
    CategoryViewModel viewModel;

    public EntertainmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEntertainmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = binding.subCategoryRv.subCategoryRv;
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        viewModel.setListener(this);
        adapter = new EntertainmentAdapter(requireActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);
        setUpEnterTainmentArticle();
    }

    private void setUpEnterTainmentArticle() {
        viewModel.getTechnologyCategory("entertainment", ApiUtil.API_KEY).observe(requireActivity(), articles -> {
            for (Article article : articles) {
                Log.d(TAG, "onActivityCreated: headlines by country " + article.getTitle());
            }
            adapter.submitList(articles);
        });
    }

    @Override
    public void shoLoading() {
        if (AppUtils.isConnected(requireActivity())) {
            binding.progressbar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            Snackbar.make(requireView(), R.string.no_internet_message,Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMovies() {
        binding.progressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNoInternet() {
        Snackbar.make(requireView(),R.string.no_internet_message,Snackbar.LENGTH_SHORT).show();
    }
}
