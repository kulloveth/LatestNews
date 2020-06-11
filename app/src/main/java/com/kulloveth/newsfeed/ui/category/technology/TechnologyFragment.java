package com.kulloveth.newsfeed.ui.category.technology;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kulloveth.newsfeed.databinding.FragmentTechnologyBinding;
import com.kulloveth.newsfeed.remote.ApiUtil;
import com.kulloveth.newsfeed.remote.model.Article;

/**
 * A simple {@link Fragment} subclass.
 */
public class TechnologyFragment extends Fragment {

    private static final String TAG = TechnologyFragment.class.getSimpleName();

    FragmentTechnologyBinding binding;
    TechnologyAdapter adapter;
    TechnologyViewModel viewModel;
    RecyclerView recyclerView;

    public TechnologyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTechnologyBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TechnologyViewModel.class);
        adapter = new TechnologyAdapter();
        recyclerView = binding.technologyRv;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);
        setUpTechnologyArticle();
    }

    private void setUpTechnologyArticle() {
        viewModel.getTechnologyCategory("technology", ApiUtil.API_KEY).observe(requireActivity(), articles -> {
            for (Article article : articles) {
                Log.d(TAG, "onActivityCreated: headlines by country " + article.getTitle());
            }
            adapter.submitList(articles);
        });
    }
}
