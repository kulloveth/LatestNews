package com.kulloveth.latestnews.remote.api;

import com.kulloveth.latestnews.remote.model.NewsResponse;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * An interface wih retrofit methods
 * to fech responses for NewsApi.org
 *
 * */

public interface ApiServiceInterface {
    @GET("v2/top-headlines")
    Call<NewsResponse> getTopHeadLinesByCountry(@Query("country") String country, @Query("apiKey") String key);

    @GET("v2/top-headlines")
    Flowable<NewsResponse> searchTopHeadLines(@Query("q") String query, @Query("apiKey") String key);

    @GET("v2/top-headlines")
    Call<NewsResponse> getTopHeadLinesByCategory(@Query("category") String category, @Query("apiKey") String key);

}
