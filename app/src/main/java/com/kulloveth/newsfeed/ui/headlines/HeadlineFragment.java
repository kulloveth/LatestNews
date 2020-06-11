package com.kulloveth.newsfeed.ui.headlines;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.kulloveth.newsfeed.AppUtils;
import com.kulloveth.newsfeed.R;
import com.kulloveth.newsfeed.databinding.FragmentHeadlineBinding;
import com.kulloveth.newsfeed.remote.ApiUtil;
import com.kulloveth.newsfeed.remote.model.Article;
import com.kulloveth.newsfeed.remote.model.NewsResponse;
import com.kulloveth.newsfeed.ui.RxSearchObservable;
import com.kulloveth.newsfeed.ui.widget.WidgetService;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HeadlineFragment extends Fragment {

    private static final String TAG = HeadlineFragment.class.getSimpleName();

    HeadlineViewModel viewModel;
    FragmentHeadlineBinding binding;
    RecyclerView recyclerView;
    HeadlineAdapter adapter;
    SearchView searchView;

    ArrayList<Article> articleArrayList = new ArrayList<>();

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


    private void setUpHeadLineArticle() {
        viewModel.getTopHeadlineByCountry("us", ApiUtil.API_KEY).observe(requireActivity(), articles -> {
            for (Article article : articles) {
                Log.d(TAG, "onActivityCreated: headlines by country " + article.getTitle());
            }
            articleArrayList = articles;
            adapter.submitList(articles);
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.headline_detail_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        setUpSearchObservable();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.update) {
            WidgetService.actionUpdateWidget(requireActivity(), articleArrayList);
            Snackbar.make(requireView(), "Widget Updated", Snackbar.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setUpSearchObservable() {
        RxSearchObservable.fromView(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(text -> {
                    if (text.isEmpty()) {
                        adapter.submitList(articleArrayList);
                        return true;
                    } else {
                        return true;
                    }
                })
                .distinctUntilChanged()
                .switchMap((Function<String, ObservableSource<ArrayList<Article>>>) query -> {
                    //query = "%" + query + "%";
                    return viewModel.searchNote(query, ApiUtil.API_KEY);

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articles -> adapter.submitList(articles), throwable -> {
                    Log.e(TAG, "setUpSearchObservable: error searching" + throwable.getMessage());
                });
    }
}
