package com.kulloveth.newsfeed.ui.headlines;

import android.os.Build;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kulloveth.newsfeed.R;
import com.kulloveth.newsfeed.databinding.FragmentHeadlineBinding;
import com.kulloveth.newsfeed.local.FavoriteEntity;
import com.kulloveth.newsfeed.remote.ApiUtil;
import com.kulloveth.newsfeed.remote.model.Article;
import com.kulloveth.newsfeed.ui.RxSearchObservable;
import com.kulloveth.newsfeed.ui.favorite.FavoriteVieModel;
import com.kulloveth.newsfeed.ui.favorite.MyViewModelFactory;
import com.kulloveth.newsfeed.ui.widget.WidgetService;
import com.kulloveth.newsfeed.utils.AppUtils;
import com.kulloveth.newsfeed.utils.ProgressListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HeadlineFragment extends Fragment implements HeadlineAdapter.ItemCLickedListener, ProgressListener {

    private static final String TAG = HeadlineFragment.class.getSimpleName();
    HeadlineAdapter.ScrollDirection scrollDirection;

    HeadlineViewModel viewModel;
    FavoriteVieModel favoriteVieModel;
    FragmentHeadlineBinding binding;
    RecyclerView recyclerView;
    private HeadlineAdapter adapter;
    private SearchView searchView;
    private Toolbar toolbar;
    private FirebaseAnalytics mFirebaseAnalytics;

    ArrayList<Article> articleArrayList = new ArrayList<>();

    public HeadlineFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHeadlineBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toolbar = binding.appBar.toolbar;
        adapter = new HeadlineAdapter(requireActivity());
        recyclerView = binding.headlineRv;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        AppUtils.setToolbarTitle(getString(R.string.headline_fragment_category), ((AppCompatActivity) requireActivity()));
        viewModel = new ViewModelProvider(requireActivity()).get(HeadlineViewModel.class);
        viewModel.setProgressListener(this);
        favoriteVieModel = new ViewModelProvider(this, new MyViewModelFactory(requireActivity().getApplication())).get(FavoriteVieModel.class);
        setUpHeadLineArticle();
        adapter.setClickedListener(this::itemClicked);
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);


    }


    private void setUpHeadLineArticle() {
        viewModel.getTopHeadlineByCountry("us", ApiUtil.API_KEY).observe(requireActivity(), articles -> {
            for (Article article : articles) {
                Log.d(TAG, "onActivityCreated: headlines by country " + article.getTitle());
            }
            articleArrayList = articles;
            adapter.submitList(articles);
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    scrollDirection = HeadlineAdapter.ScrollDirection.DOWN;
                } else {
                    scrollDirection = HeadlineAdapter.ScrollDirection.TOP;
                }
            }
        });
        adapter.scrollDirection = scrollDirection;
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
        switch (itemId) {
            case R.id.update:
                WidgetService.actionUpdateWidget(requireActivity(), articleArrayList);
                Snackbar.make(requireView(), R.string.widget_update_msg, Snackbar.LENGTH_LONG).show();
                return true;

            case R.id.filter:
                showAlertDialog();
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
                    return viewModel.searchHeadLine(query, ApiUtil.API_KEY);

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articles -> adapter.submitList(articles), throwable -> {
                    Log.e(TAG, "setUpSearchObservable: error searching" + throwable.getMessage());
                });
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireActivity());
        alertDialog.setTitle("AlertDialog");
        String[] items = {"Germany", "United States", "Japan", "Nigeria", "California"};
        int checkedItem = 1;
        alertDialog.setSingleChoiceItems(items, checkedItem, (dialog, which) -> {
            switch (which) {
                case 0:
                    viewModel.getTopHeadlineByCountry("de", ApiUtil.API_KEY);
                    AppUtils.setToolbarTitle(getString(R.string.de_title), ((AppCompatActivity) requireActivity()));
                    break;
                case 1:
                    viewModel.getTopHeadlineByCountry("us", ApiUtil.API_KEY);
                    AppUtils.setToolbarTitle(getString(R.string.u_title), ((AppCompatActivity) requireActivity()));
                    break;
                case 2:
                    viewModel.getTopHeadlineByCountry("jp", ApiUtil.API_KEY);
                    AppUtils.setToolbarTitle("Japan", ((AppCompatActivity) requireActivity()));
                    break;
                case 3:
                    viewModel.getTopHeadlineByCountry("ng", ApiUtil.API_KEY);
                    AppUtils.setToolbarTitle(getString(R.string.ng_title), ((AppCompatActivity) requireActivity()));
                    break;
                case 4:
                    viewModel.getTopHeadlineByCountry("ca", ApiUtil.API_KEY);
                    AppUtils.setToolbarTitle(getString(R.string.california_title), ((AppCompatActivity) requireActivity()));
                    break;
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    @Override
    public void itemClicked(Article article, int position) {
        FavoriteEntity favoriteEntity = new FavoriteEntity(position, article.getTitle(), article.getDescription(), article.getUrlToImage());
        favoriteVieModel.insertFavorite(favoriteEntity);
        Snackbar.make(requireView(), R.string.liked_article_mesage, Snackbar.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, article.getTitle());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, article.getUrlToImage());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    @Override
    public void shoLoading() {
        if (AppUtils.isConnected(requireActivity())) {
            binding.progressbar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            Snackbar.make(requireView(), R.string.no_internet_message, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMovies() {
        binding.progressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNoInternet() {
        Snackbar.make(requireView(), R.string.no_internet_message, Snackbar.LENGTH_SHORT).show();
    }
}
