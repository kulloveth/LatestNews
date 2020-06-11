package com.kulloveth.newsfeed.ui.headlines;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kulloveth.newsfeed.R;
import com.kulloveth.newsfeed.databinding.FragmentHeadlineBinding;
import com.kulloveth.newsfeed.remote.ApiUtil;
import com.kulloveth.newsfeed.remote.model.Article;

import java.util.List;

public class HeadlineFragment extends Fragment {

    private static final String TAG = HeadlineFragment.class.getSimpleName();

    HeadlineViewModel viewModel;
    FragmentHeadlineBinding binding;

    public HeadlineFragment() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHeadlineBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HeadlineViewModel.class);

        viewModel.getTopHeadlineByCountry("us", ApiUtil.API_KEY).observe(requireActivity(), articles -> {
            Log.d(TAG, "onActivityCreated: headlines by country " + articles);
        });
    }
}
