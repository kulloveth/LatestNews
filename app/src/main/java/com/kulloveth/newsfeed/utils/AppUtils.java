package com.kulloveth.newsfeed.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import com.kulloveth.newsfeed.R;

import java.util.Objects;

public class AppUtils {

    public static void setToolbarTitle(String title, AppCompatActivity context) {
        Objects.requireNonNull(context.getSupportActionBar()).setTitle(title);
    }

    public static void shareNewsTitle(Context context, Activity activity, String title) {
        context.startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setText(title)
                .getIntent(), context.getString((R.string.news_headline))));
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
