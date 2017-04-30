package com.deltabit.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.deltabit.bakingapp.model.Recipe;

public class StepListActivity extends AppCompatActivity {

    Recipe selectedRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        selectedRecipe = ((BakingAppApplication)getApplicationContext()).getSelectedRecipe();


    }
}
