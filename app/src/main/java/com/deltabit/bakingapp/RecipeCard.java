package com.deltabit.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by rigel on 29/04/17.
 */

public class RecipeCard {

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
                ((BakingAppApplication) context.getApplicationContext()).setSelectedRecipe(recipe);
                Intent i = new Intent(context, StepListActivity.class);
                context.startActivity(i);
            }
        });

        return card;

    }
}
