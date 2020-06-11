package com.kulloveth.newsfeed.remote;

import com.kulloveth.newsfeed.remote.api.ApiServiceInterface;
import com.kulloveth.newsfeed.remote.api.RetrofitClient;

public class ApiUtil {

    private static final String BASE_API_URL = "https://newsapi.org/";

    public static final String API_KEY = "d2691289ff474bb9850b71fa026ce470";

    public static ApiServiceInterface getNewsApiServiceInterface() {
        return RetrofitClient.getRetrofitClient(BASE_API_URL).create(ApiServiceInterface.class);
    }
}
