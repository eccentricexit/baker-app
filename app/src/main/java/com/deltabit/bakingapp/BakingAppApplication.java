package com.deltabit.bakingapp;

import android.app.Application;

import com.deltabit.bakingapp.model.Recipe;
import com.deltabit.bakingapp.model.Step;

/**
 * Created by rigel on 30/04/17.
 */

public class BakingAppApplication extends Application {
    private Recipe selectedRecipe;
    private Step selectedStep;

    public Recipe getSelectedRecipe() {
        return selectedRecipe;
    }
    public void setSelectedRecipe(Recipe selectedRecipe) {
        this.selectedRecipe = selectedRecipe;
    }

    public Step getSelectedStep() {
        return selectedStep;
    }
    public void setSelectedStep(Step selectedStep) {
        this.selectedStep = selectedStep;
    }

}
