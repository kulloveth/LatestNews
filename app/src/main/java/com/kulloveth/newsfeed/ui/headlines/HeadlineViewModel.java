package com.kulloveth.newsfeed.ui.headlines;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kulloveth.newsfeed.remote.ApiUtil;
import com.kulloveth.newsfeed.remote.api.ApiServiceInterface;
import com.kulloveth.newsfeed.remote.model.Article;
import com.kulloveth.newsfeed.remote.model.NewsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeadlineViewModel extends ViewModel {

    private static final String TAG = HeadlineViewModel.class.getSimpleName();

    private MutableLiveData<List<Article>> articlesLiveData;
    private ApiServiceInterface apiServiceInterface;

    public HeadlineViewModel() {
        articlesLiveData = new MutableLiveData<>();
        apiServiceInterface = ApiUtil.getNewsApiServiceInterface();
    }

    //fetch topheadline by countries
    LiveData<List<Article>> getTopHeadlineByCountry(String country, String apiKey) {
        apiServiceInterface.getTopHeadLinesByCountry(country, apiKey).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    List<Article> articles = response.body().getArticles();
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
}
