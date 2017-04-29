package com.deltabit.bakingapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.deltabit.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.IconSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.TextSupplementalAction;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

public class RecipeActivity extends AppCompatActivity {

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
        //TODO: Add animation
        //mRecyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));

        //Set the empty view
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mCardArrayAdapter);
        }

        //Load cards
        new LoaderAsyncTask().execute();
    }

    private ArrayList<Card> initCard() {
        Recipe dummyRecipe = new Recipe();
        dummyRecipe.setName("Nutella Pie");
        dummyRecipe.setImage("http://ichef.bbci.co.uk/news/660/cpsprodpb/1325A/production/_88762487_junk_food.jpg");

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(RecipeCard.buildRecipeCard(context,dummyRecipe));
        cards.add(RecipeCard.buildRecipeCard(context,dummyRecipe));
        cards.add(RecipeCard.buildRecipeCard(context,dummyRecipe));
        cards.add(RecipeCard.buildRecipeCard(context,dummyRecipe));

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
            //elaborate images
            //SystemClock.sleep(1000); //delay to simulate download, don't use it in a real app

            ArrayList<Card> cards = initCard();
            return cards;
        }

        @Override
        protected void onPostExecute(ArrayList<Card> cards) {
            //Update the adapter
            updateAdapter(cards);
        }
    }
}
