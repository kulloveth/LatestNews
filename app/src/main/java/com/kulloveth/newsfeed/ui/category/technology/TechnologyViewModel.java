package com.kulloveth.newsfeed.ui.category.technology;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kulloveth.newsfeed.remote.ApiUtil;
import com.kulloveth.newsfeed.remote.api.ApiServiceInterface;
import com.kulloveth.newsfeed.remote.model.Article;
import com.kulloveth.newsfeed.remote.model.NewsResponse;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TechnologyViewModel extends ViewModel {

    private static final String TAG = TechnologyViewModel.class.getSimpleName();

    private MutableLiveData<ArrayList<Article>> articlesLiveData;
    private ApiServiceInterface apiServiceInterface;
    CompositeDisposable disposable;

    public TechnologyViewModel() {
        articlesLiveData = new MutableLiveData<>();
        apiServiceInterface = ApiUtil.getNewsApiServiceInterface();
        disposable = new CompositeDisposable();
    }

    //fetch topheadline by countries
    public  LiveData<ArrayList<Article>> getTechnologyCategory(String category, String apiKey) {
        apiServiceInterface.getTopHeadLinesByCategory(category, apiKey).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Article> articles = response.body().getArticles();
                    articlesLiveData.setValue(articles);
                } else {
                    Log.e(TAG, "onResponse: Error fetching data" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: error" + t.getMessage());
            }
        });
        return articlesLiveData;
    }

    Observable searchNote(String query, String apikey) {
        Observable observable = apiServiceInterface.searchTopHeadLines(query, apikey).delay(2, TimeUnit.SECONDS)
                .map((Function<NewsResponse, Object>) articles ->
                        articles.getArticles()).toObservable();
        disposable.add(observable.subscribe());
        return observable;


    }

    @Override
    protected void onCleared() {
        disposable.dispose();
    }
}
