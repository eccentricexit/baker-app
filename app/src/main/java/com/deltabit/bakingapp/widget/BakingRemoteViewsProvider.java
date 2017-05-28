package com.deltabit.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.deltabit.bakingapp.R;
import com.deltabit.bakingapp.RecipeActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingRemoteViewsProvider extends AppWidgetProvider {
    private static final String LOG_TAG = BakingRemoteViewsProvider.class.getSimpleName();
    Context context;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        Log.d(LOG_TAG, "onUpdate()");
        this.context = context;

        for (int appWidgetId = 0; appWidgetId < appWidgetIds.length; appWidgetId++) {
            Intent intent = new Intent(context, BakingRemoteViewsService.class);

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[appWidgetId]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews widget = new RemoteViews(context.getPackageName(),
                    R.layout.baker_app_widget);

            widget.setRemoteAdapter(appWidgetIds[appWidgetId], R.id.ingredients_listview,
                    intent);

            Intent clickIntent = new Intent(context, RecipeActivity.class);
            PendingIntent openAppIntent = PendingIntent
                    .getActivity(context, 0,
                            clickIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            widget.setPendingIntentTemplate(R.id.ingredients_listview, openAppIntent);

            appWidgetManager.updateAppWidget(appWidgetIds[appWidgetId], widget);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, appWidgetId);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "onReceive() ");
        this.context = context;
        if (intent.getAction().equalsIgnoreCase(AppWidgetManager.ACTION_APPWIDGET_UPDATE))
            updateWidget();

        super.onReceive(context, intent);
    }

    private void updateWidget() {
        if (context == null) {
            Log.d(LOG_TAG, "context == null");
            return;
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingRemoteViewsProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredients_listview);
    }
}

