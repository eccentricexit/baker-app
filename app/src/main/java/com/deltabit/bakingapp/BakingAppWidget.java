package com.deltabit.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    private static final String LOG_TAG = BakingAppWidget.class.getSimpleName();


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baker_app_widget);
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.SHARED_PREFERENCES_KEY),
                MODE_PRIVATE
        );

        if(sharedPreferencesContainsAllKeys(sharedPreferences,context)){
            Log.d(LOG_TAG,"keys found.");

            String title = sharedPreferences.getString(context.getString(R.string.RECIPE_TITLE_KEY),"");
            String stepShortDesc = sharedPreferences
                    .getString(context.getString(R.string.SELECTED_STEP_TITLE_KEY),"Touch to open app.");

            views.setTextViewText(R.id.recipe_title_widget,title);
            views.setTextViewText(R.id.recipe_step_short_desc_widget,stepShortDesc);

            views.setOnClickPendingIntent(R.id.linearlLayout_recipe_widget,null);
        }else {
            Log.d(LOG_TAG, "no key found.");
            views.setTextViewText(R.id.recipe_step_short_desc_widget, "Touch to open app.");

            Intent intent = new Intent(context,RecipeActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
            views.setOnClickPendingIntent(R.id.linearlLayout_recipe_widget,pendingIntent);
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, BakingAppWidget.class.getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    private static boolean sharedPreferencesContainsAllKeys(SharedPreferences sharedPreferences, Context context) {
        return sharedPreferences.contains(context.getString(R.string.SELECTED_RECIPE_ID_KEY)) &&
                sharedPreferences.contains(context.getString(R.string.SELECTED_STEP_ID_KEY)) &&
                sharedPreferences.contains(context.getString(R.string.RECIPE_TITLE_KEY)) &&
                sharedPreferences.contains(context.getString(R.string.SELECTED_STEP_TITLE_KEY));
    }



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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

