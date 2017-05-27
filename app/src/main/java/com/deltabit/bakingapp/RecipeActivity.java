package com.deltabit.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.deltabit.bakingapp.model.Recipe;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.TextSupplementalAction;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class RecipeActivity extends AppCompatActivity {

    private static final String LOG_TAG = RecipeActivity.class.getSimpleName();
    private static final String RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017" +
                                               "/May/5907926b_baking/baking.json";

    private CardArrayRecyclerViewAdapter mCardArrayAdapter;
    private Context context;

    @BindView(R.id.progressBar_recipes)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        context = this;

        ArrayList<Card> cards = new ArrayList<>();
        mCardArrayAdapter = new CardArrayRecyclerViewAdapter(context, cards);

        CardRecyclerView mRecyclerView = (CardRecyclerView) findViewById(R.id.carddemo_recyclerview2);
        mRecyclerView.setHasFixedSize(false);
        int orientation = getResources().getConfiguration().orientation;

        if(orientation==ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        }else{
            mRecyclerView.setLayoutManager(new GridLayoutManager(context,3));
        }

        //Set the empty view
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mCardArrayAdapter);
        }

        //Load cards
        new LoaderAsyncTask().execute();
    }

    private ArrayList<Card> initCard() {
        List<Recipe> recipes = null;
        try {
            Request request = new Request.Builder()
                    .url(RECIPES_URL)
                    .build();

            Log.d(LOG_TAG,"inside init card");

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30,TimeUnit.SECONDS)
                    .readTimeout(30,TimeUnit.SECONDS)
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .build();

            Response response = client.newCall(request).execute();
            String jsonRecipes = response.body().string();

            recipes = Recipe.getRecipiesFromJson(jsonRecipes);
            ((BakingApplication) context.getApplicationContext()).setRecipies(recipes);


        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Card> cards = new ArrayList<>();

        int id = 0;
        if(recipes!=null)
            for (Recipe recipe : recipes)
                cards.add(buildRecipeCard(context, recipe,id++));
        else
            Log.d(LOG_TAG,"recipes == null");


        return cards;
    }

    private void updateAdapter(ArrayList<Card> cards) {
        if (cards != null) {
            mCardArrayAdapter.addAll(cards);
        }
    }

    class LoaderAsyncTask extends AsyncTask<Void, Void, ArrayList<Card>> {

        LoaderAsyncTask() {}

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Card> doInBackground(Void... params) {
            ArrayList<Card> cards = initCard();
            return cards;
        }

        @Override
        protected void onPostExecute(ArrayList<Card> cards) {
            //Update the adapter
            progressBar.setVisibility(View.GONE);
            updateAdapter(cards);
        }
    }

    private static final String NO_PREVIEW_AVAILABLE_IMAGE = "http://www.finescale.com/sitefiles/" +
                                                             "images/no-preview-available.png";

    public static MaterialLargeImageCard buildRecipeCard(
            final Context context,
            final Recipe recipe,
            final int id
    )
    {

        ArrayList<BaseSupplementalAction> actions = new ArrayList<>();
        TextSupplementalAction t1 = new TextSupplementalAction(context, R.id.text1);
        t1.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "What do you think of " + recipe.getName();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Recipe");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);

                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        actions.add(t1);

        TextSupplementalAction t2 = new TextSupplementalAction(context, R.id.text2);
        t2.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                ((BakingApplication) context.getApplicationContext()).setSelectedStepId(id);
                saveSelectedRecipeToSharedPreferences(id,recipe,context);
                Intent i = new Intent(context, StepListActivity.class);
                context.startActivity(i);
            }
        });
        actions.add(t2);

        //Create a Card, set the title over the image and set the thumbnail
        MaterialLargeImageCard card = MaterialLargeImageCard.with(context)
                .setTextOverImage(recipe.getName())
                .useDrawableExternal(new MaterialLargeImageCard.DrawableExternal() {
                    @Override
                    public void setupInnerViewElements(ViewGroup parent, View viewImage) {
                        if (recipe.getImage() != null || !recipe.getImage().equals(""))
                            recipe.setImage(Util.PLACEHOLDER_URL);

                        Picasso.with(context)
                                .load(recipe.getImage())
                                .placeholder(R.drawable.nopreviewavailable)
                                .error(R.drawable.nopreviewavailable)
                                .into((ImageView) viewImage);

                    }
                })
                .setupSupplementalActions(R.layout.carddemo_native_material_supplemental_actions_large, actions)
                .build();

        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {

                ((BakingApplication) context.getApplicationContext()).setSelectedStepId(id);

                saveSelectedRecipeToSharedPreferences(id,recipe,context);

                Intent i = new Intent(context, StepListActivity.class);
                context.startActivity(i);
            }
        });

        return card;
    }

    public static void saveSelectedRecipeToSharedPreferences(int selectedRecipeId,Recipe selectedRecipe,Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(
                context.getString(R.string.SHARED_PREFERENCES_KEY),
                MODE_PRIVATE
        ).edit();


        editor.putString(context.getString(R.string.RECIPE_TITLE_KEY),selectedRecipe.getName());
        editor.putInt(context.getString(R.string.SELECTED_RECIPE_ID_KEY), selectedRecipeId);
        String ingredientsJson = new Gson().toJson(selectedRecipe.getIngredients());
        Log.d(LOG_TAG,"saving ingredientsJson to sharedPreferences: "+ingredientsJson);

        editor.putString(context.getString(R.string.INGREDIENTS_KEY), ingredientsJson);

        editor.commit();
    }

    private  <T> void setList(String key, List<T> list, SharedPreferences.Editor editor) {
        editor.putString(key, new Gson().toJson(list));
    }

    private boolean isTabletInLandscapeMode() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int widthPixels = metrics.widthPixels;

        float scaleFactor = metrics.density;

        float widthDp = widthPixels / scaleFactor;

        return widthDp>=900;
    }


}
