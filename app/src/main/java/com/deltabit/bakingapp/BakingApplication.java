package com.deltabit.bakingapp;

import android.app.Application;

import com.deltabit.bakingapp.model.Recipe;
import com.deltabit.bakingapp.model.Step;

import java.util.List;

/**
 * Created by rigel on 30/04/17.
 */

public class BakingApplication extends Application {
    private List<Recipe> recipies;
    private int selectedStepId;
    private int selectedRecipeId;

    public List<Step> getSteps() {
        return recipies.get(selectedRecipeId).getSteps();
    }

    public int getSelectedStepId() {
        return selectedStepId;
    }

    public void setSelectedStepId(int selectedStepId) {
        this.selectedStepId = selectedStepId;
    }

    public Recipe getSelectedRecipe() {
        return recipies.get(selectedRecipeId);
    }

    public List<Recipe> getRecipies() {
        return recipies;
    }

    public void setRecipies(List<Recipe> recipies) {
        this.recipies = recipies;
    }


}
