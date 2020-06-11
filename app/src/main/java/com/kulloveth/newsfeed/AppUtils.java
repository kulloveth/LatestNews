package com.kulloveth.newsfeed;


import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class AppUtils {

    public static void setToolbarTitle(String title, AppCompatActivity context) {
       Objects.requireNonNull(context.getSupportActionBar()).setTitle(title);
    }
}
