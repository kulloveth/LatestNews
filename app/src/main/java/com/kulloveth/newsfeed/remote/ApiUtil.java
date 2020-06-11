package com.kulloveth.newsfeed.remote;

import com.kulloveth.newsfeed.BuildConfig;
import com.kulloveth.newsfeed.remote.api.ApiServiceInterface;
import com.kulloveth.newsfeed.remote.api.RetrofitClient;

public class ApiUtil {

    private static final String BASE_API_URL = "https://newsapi.org/";

    public static final String API_KEY = BuildConfig.API_KEY;

    public static ApiServiceInterface getNewsApiServiceInterface() {
        return RetrofitClient.getRetrofitClient(BASE_API_URL).create(ApiServiceInterface.class);
    }
}
