package com.deltabit.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.deltabit.bakingapp.IngredientActivity;
import com.deltabit.bakingapp.R;
import com.deltabit.bakingapp.model.Ingredient;
import com.deltabit.bakingapp.model.Recipe;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rigel on 27/05/17.
 */

public class BakingRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String LOG_TAG = BakingRemoteViewsFactory.class.getSimpleName();
    private static Ingredient[] ingredients = new Ingredient[]{new Ingredient("","No recipe selected")};

    private Context ctxt=null;
    private int appWidgetId;

    public BakingRemoteViewsFactory(Context context, Intent intent) {
        this.ctxt=context;
        appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        SharedPreferences sharedPreferences = context
                .getSharedPreferences(context.getString(R.string.SHARED_PREFERENCES_KEY),MODE_PRIVATE);

        String ingredientsJson = sharedPreferences.getString(context.getString(R.string.INGREDIENTS_KEY),null);
        Log.d(LOG_TAG,"got ingredientsJson from sharedPreferences: "+ingredientsJson);
        if(ingredientsJson==null || ingredientsJson.equals(""))
            displayNoRecipeSelectedMessage();
        else{
            ingredients = new Gson().fromJson(ingredientsJson,Ingredient[].class);
        }


    }



    private void displayNoRecipeSelectedMessage() {
        Log.d(LOG_TAG,"ingredientsJson == null || ingredientsJson.equals(\"\")");
    }

    @Override
    public void onCreate() {    }

    @Override
    public void onDestroy() {    }

    @Override
    public int getCount() {
        return(ingredients.length);
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row=new RemoteViews(ctxt.getPackageName(),
                R.layout.ingredient_item);

        row.setTextViewText(R.id.textview_name_list_item, ingredients[position].getIngredient());
        row.setTextViewText(R.id.textview_quantity_list_item,
                ingredients[position].getQuantity()+ingredients[position].getMeasure()
        );
        Log.d(LOG_TAG,"getViewAt(): "+ingredients[position].getIngredient());

        Intent i=new Intent();
        Bundle extras=new Bundle();

        extras.putString(BakingRemoteViewsProvider.EXTRA_WORD,
                ingredients[position].getIngredient()
        );
        i.putExtras(extras);
        row.setOnClickFillInIntent(R.id.textview_name_list_item, i);

        return(row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return(null);
    }

    @Override
    public int getViewTypeCount() {
        return(1);
    }

    @Override
    public long getItemId(int position) {
        return(position);
    }

    @Override
    public boolean hasStableIds() {
        return(true);
    }

    @Override
    public void onDataSetChanged() {    }


}