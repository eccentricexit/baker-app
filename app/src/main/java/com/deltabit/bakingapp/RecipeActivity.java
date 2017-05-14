package com.deltabit.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.deltabit.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.TextSupplementalAction;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecipeActivity extends AppCompatActivity {

    private static final String LOG_TAG = RecipeActivity.class.getSimpleName();
    private static final String RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017" +
                                               "/May/5907926b_baking/baking.json";
    private CardArrayRecyclerViewAdapter mCardArrayAdapter;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        context = this;


        ArrayList<Card> cards = new ArrayList<>();

        mCardArrayAdapter = new CardArrayRecyclerViewAdapter(context, cards);

        CardRecyclerView mRecyclerView = (CardRecyclerView) findViewById(R.id.carddemo_recyclerview2);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        //Set the empty view
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mCardArrayAdapter);
        }

        //Load cards
        new LoaderAsyncTask().execute();
    }

    private ArrayList<Card> initCard() {
        List<Recipe> recipies = null;
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

            recipies = Recipe.getRecipiesFromJson(jsonRecipes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Card> cards = new ArrayList<>();

        if(recipies!=null)
            for (Recipe recipe : recipies)
                cards.add(buildRecipeCard(context, recipe));
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
        protected ArrayList<Card> doInBackground(Void... params) {
            ArrayList<Card> cards = initCard();
            return cards;
        }

        @Override
        protected void onPostExecute(ArrayList<Card> cards) {
            //Update the adapter
            updateAdapter(cards);
        }
    }

    private static final String NO_PREVIEW_AVAILABLE_IMAGE = "http://www.finescale.com/sitefiles/images/no-preview-available.png";

    public static MaterialLargeImageCard buildRecipeCard(final Context context, final Recipe recipe) {

        ArrayList<BaseSupplementalAction> actions = new ArrayList<>();
        TextSupplementalAction t1 = new TextSupplementalAction(context, R.id.text1);
        t1.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(context, " Click on Text SHARE ", Toast.LENGTH_SHORT).show();

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
                ((BakingAppApplication) context.getApplicationContext()).setSteps(recipe.getSteps());
                ((BakingAppApplication) context.getApplicationContext()).setSelectedRecipe(recipe);
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
                ((BakingAppApplication) context.getApplicationContext()).setSteps(recipe.getSteps());
                ((BakingAppApplication) context.getApplicationContext()).setSelectedRecipe(recipe);
                Intent i = new Intent(context, StepListActivity.class);
                context.startActivity(i);
            }
        });

        return card;

    }


}
