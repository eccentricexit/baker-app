package com.deltabit.bakingapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.IconSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.TextSupplementalAction;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by rigel on 29/04/17.
 */

public class RecipeCard{

    public static MaterialLargeImageCard buildRecipeCard(final Context context) {

        ArrayList<BaseSupplementalAction> actions = new ArrayList<>();
        TextSupplementalAction t1 = new TextSupplementalAction(context, R.id.text1);
        t1.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(context, " Click on Text SHARE ", Toast.LENGTH_SHORT).show();
            }
        });
        actions.add(t1);

        TextSupplementalAction t2 = new TextSupplementalAction(context, R.id.text2);
        t2.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(context, " Click on Text LEARN ", Toast.LENGTH_SHORT).show();
            }
        });
        actions.add(t2);

        //Create a Card, set the title over the image and set the thumbnail
        MaterialLargeImageCard card = MaterialLargeImageCard.with(context)
                .setTextOverImage("Chicken with some green things")
                .useDrawableExternal(new MaterialLargeImageCard.DrawableExternal() {
                    @Override
                    public void setupInnerViewElements(ViewGroup parent, View viewImage) {

                        Picasso.with(context).setIndicatorsEnabled(true);  //only for debug tests
                        Picasso.with(context)
                                .load("http://southernglutenfree.com/wp-content/uploads/2016/01/moroccan-stuffed-onions-recipe-1060.jpg")
                                .into((ImageView) viewImage);
                    }
                })
                .setupSupplementalActions(R.layout.carddemo_native_material_supplemental_actions_large, actions)
                .build();

        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(context, " Click on ActionArea ", Toast.LENGTH_SHORT).show();
            }
        });

        return card;

    }
}
