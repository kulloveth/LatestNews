package com.kulloveth.latestnews.ui.favorite;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.kulloveth.latestnews.utils.AppUtils;
import com.kulloveth.latestnews.R;
import com.kulloveth.latestnews.databinding.FragmentFavoriteBinding;
import com.kulloveth.latestnews.local.FavoriteEntity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment  implements FavoriteAdapter.ItemCLickedListener {

    FragmentFavoriteBinding binding;
    FavoriteVieModel favoriteVieModel;
    private static final String TAG = FavoriteFragment.class.getSimpleName();

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = binding.appBar.toolbar;
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        AppUtils.setToolbarTitle(getString(R.string.favorite_fragment_title), ((AppCompatActivity) requireActivity()));

        FavoriteAdapter adapter = new FavoriteAdapter(requireActivity());
        RecyclerView recyclerView = binding.headlineRv;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setClickedListener(this);

        favoriteVieModel = new ViewModelProvider(this).get(FavoriteVieModel.class);
        favoriteVieModel.fetchFavoriteEntity().observe(requireActivity(), favorites ->{
                Log.d(TAG, "onActivityCreated: "+ favorites.size());
            adapter.submitList(favorites);});
    }

    @Override
    public void itemClicked(FavoriteEntity article, int position) {
        favoriteVieModel.deleteFavorite(article);
        Snackbar.make(requireView(), "you unliked an article", Snackbar.LENGTH_SHORT).show();
    }
}
