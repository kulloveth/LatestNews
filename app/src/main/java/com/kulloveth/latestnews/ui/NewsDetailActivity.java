package com.kulloveth.latestnews.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.kulloveth.latestnews.databinding.ActivityNewsDetailBinding;

public class NewsDetailActivity extends AppCompatActivity {

    private static String EXTRA_URL;
    private ActivityNewsDetailBinding binding;

    public static void start(Context caller, String url){
        Intent intent = new Intent(caller, NewsDetailActivity.class);
        intent.putExtra(EXTRA_URL, url);
        caller.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        WebView webView = binding.webview;
        if (getIntent().getStringExtra(EXTRA_URL) != null)
        webView.loadUrl(getIntent().getStringExtra(EXTRA_URL));
        else {
            webView.setVisibility(View.GONE);
        }
    }


}