package com.kulloveth.latestnews.ui.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.kulloveth.latestnews.remote.model.Article;

import java.util.ArrayList;

import static com.kulloveth.latestnews.ui.widget.NewsWidget.ACTION_UPDATE;
import static com.kulloveth.latestnews.ui.widget.NewsWidget.HEADLINE_TITLE;

public class WidgetService extends IntentService {


    public WidgetService() {
        super("Widget Service");
    }

    public static void actionUpdateWidget(Context context, ArrayList<Article> articles) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.setAction(ACTION_UPDATE);
        intent.putParcelableArrayListExtra(HEADLINE_TITLE, articles);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE.equals(action)) {
                ArrayList<Article> articles = intent.getParcelableArrayListExtra(HEADLINE_TITLE);
                handleActionUpdate(articles);
            }
        }
    }

    private void handleActionUpdate(ArrayList<Article> articles) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, NewsWidget.class));
        NewsWidget.updateIngredientWidget(this, appWidgetManager, articles, widgetIds);
    }
}
