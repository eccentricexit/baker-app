package com.deltabit.bakingapp;

import android.app.Application;

import com.deltabit.bakingapp.model.Recipe;
import com.deltabit.bakingapp.model.Step;

import java.util.List;

/**
 * Created by rigel on 30/04/17.
 */

public class BakingAppApplication extends Application {
    private Recipe selectedRecipe;
    private List<Step> steps;
    private int selectedStep;

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getSelectedStep() {
        return selectedStep;
    }

    public void setSelectedStep(int selectedStep) {
        this.selectedStep = selectedStep;
    }
    public Recipe getSelectedRecipe() {
        return selectedRecipe;
    }
    public void setSelectedRecipe(Recipe selectedRecipe) {
        this.selectedRecipe = selectedRecipe;
    }



}
