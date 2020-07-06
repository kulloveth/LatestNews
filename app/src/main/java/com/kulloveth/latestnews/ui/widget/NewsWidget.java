package com.kulloveth.latestnews.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.kulloveth.latestnews.R;
import com.kulloveth.latestnews.remote.model.Article;
import com.kulloveth.latestnews.ui.MainActivity;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class NewsWidget extends AppWidgetProvider {

    public static final String HEADLINE_TITLE = "headline_title";
    public static final String ACTION_UPDATE = "action_update";
    public static final String IS_INTENTFROMWDGET_KEY = "isintentfrom_key";

    static void updateAppWidget(Context context, ArrayList<Article> articles, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putExtra(HEADLINE_TITLE, articles);
        intent.putExtra(IS_INTENTFROMWDGET_KEY, true);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.headline_titles);
        remoteViews.removeAllViews(R.id.headline_title_list);
        remoteViews.setOnClickPendingIntent(R.id.widget_container, pendingIntent);

        for (Article article : articles) {
            String title = article.getTitle();
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_widget);
            views.setTextViewText(R.id.appwidget_text, title);
            remoteViews.addView(R.id.headline_title_list, views);
        }
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }


    public static void updateIngredientWidget(Context context, AppWidgetManager appWidgetManager, ArrayList<Article> articles, int[] widgetIds) {
        for (int appWidgetId : widgetIds) {
            updateAppWidget(context, articles, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

