package com.deltabit.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StepDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = StepDetailActivity.class.getSimpleName();
    private BakingAppApplication application;
    private StepDetailFragment fragment;
    private Button btnPrevious;
    private Button btnNextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        application = (BakingAppApplication) this.getApplicationContext();
        btnPrevious = (Button) findViewById(R.id.btnPreviousStep);
        btnNextStep = (Button) findViewById(R.id.btnNextStep);
        updateNavigationButtons();
        setupFragment(savedInstanceState);
    }

    private void setupFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(StepDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(StepDetailFragment.ARG_ITEM_ID));

            fragment = new StepDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, fragment)
                    .commit();
        }
    }

    private void updateNavigationButtons() {
        if(application.getSelectedStep()==0)
            btnPrevious.setEnabled(false);
        else
            btnPrevious.setEnabled(true);

        if(application.getSelectedStep()==application.getSteps().size()-1)
            btnNextStep.setEnabled(false);
        else
            btnNextStep.setEnabled(true);
    }

    public void btnPreviousClick(View view) {
        Log.d(LOG_TAG,"previous button clicked");
        application.setSelectedStep(application.getSelectedStep()-1);
        fragment.updateSelectedStepInfo();
        updateNavigationButtons();
    }

    public void btnNextClick(View view) {
        Log.d(LOG_TAG,"next button clicked");
        application.setSelectedStep(application.getSelectedStep()+1);
        fragment.updateSelectedStepInfo();
        updateNavigationButtons();
    }
}
