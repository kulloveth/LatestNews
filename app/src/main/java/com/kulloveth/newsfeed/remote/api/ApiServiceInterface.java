package com.kulloveth.newsfeed.remote.api;

import com.kulloveth.newsfeed.remote.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceInterface {
    @GET("v2/top-headlines")
    Call<NewsResponse> getTopHeadLinesByCountry(@Query("country") String country, @Query("apiKey") String key);

    @GET("v2/top-headlines")
    Call<NewsResponse> getTopHeadLinesByCategory(@Query("category") String category, @Query("apiKey") String key);

}
