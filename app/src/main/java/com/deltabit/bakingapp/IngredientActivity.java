package com.deltabit.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.deltabit.bakingapp.model.Ingredient;

public class IngredientActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(StepDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(StepDetailFragment.ARG_ITEM_ID));

            IngredientFragment fragment = new IngredientFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, fragment)
                    .commit();
        }
    }
}
