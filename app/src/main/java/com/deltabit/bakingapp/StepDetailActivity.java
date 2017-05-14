package com.deltabit.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        application = (BakingAppApplication) this.getApplicationContext();
        btnPrevious = (Button) findViewById(R.id.btnPreviousStep);
        btnNextStep = (Button) findViewById(R.id.btnNextStep);
        sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFERENCES_KEY),MODE_PRIVATE);

        setupFragment(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sharedPreferencesContainsAllKeys(sharedPreferences,this)){
            int stepId = sharedPreferences.getInt(getString(R.string.SELECTED_STEP_ID_KEY),0);
            application.setSelectedStepId(stepId);
        }

        updateNavigationButtons();
    }

    private void setupFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragment = new StepDetailFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, fragment)
                    .commit();
        }
    }

    private void updateNavigationButtons() {
        if(application.getSelectedStepId()==0)
            btnPrevious.setEnabled(false);
        else
            btnPrevious.setEnabled(true);

        if(application.getSelectedStepId()==application.getSteps().size()-1)
            btnNextStep.setEnabled(false);
        else
            btnNextStep.setEnabled(true);
    }

    public void btnPreviousClick(View view) {
        Log.d(LOG_TAG,"previous button clicked");
        application.setSelectedStepId(application.getSelectedStepId()-1);

        int currentStepId = application.getSelectedStepId();
        fragment.updateSelectedStepInfo(application.getSteps().get(currentStepId));
        updateNavigationButtons();

        saveStepToSharedPreferences(
                currentStepId,
                application
                        .getSteps()
                        .get(currentStepId)
                        .getShortDescription()
        );

        requestWidgetUpdate();
    }

    public void btnNextClick(View view) {
        Log.d(LOG_TAG,"next button clicked");
        application.setSelectedStepId(application.getSelectedStepId()+1);

        int currentStepId = application.getSelectedStepId();
        fragment.updateSelectedStepInfo(application.getSteps().get(application.getSelectedStepId()));
        updateNavigationButtons();

        saveStepToSharedPreferences(
                currentStepId,
                application
                        .getSteps()
                        .get(currentStepId)
                        .getShortDescription()
        );


        requestWidgetUpdate();
    }

    private void saveStepToSharedPreferences(int selectedStepId,String selectedStepTitle){

        SharedPreferences.Editor editor = getSharedPreferences(
                getString(R.string.SHARED_PREFERENCES_KEY),
                MODE_PRIVATE
        ).edit();

        editor.putInt(getString(R.string.SELECTED_STEP_ID_KEY), selectedStepId);
        editor.putString(getString(R.string.SELECTED_STEP_TITLE_KEY),selectedStepTitle);

        editor.commit();
    }

    private void requestWidgetUpdate(){
        Intent intent = new Intent(this, BakingAppWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), BakingAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);

        Log.d(LOG_TAG,"Sending update broadcast");
    }

    private boolean sharedPreferencesContainsAllKeys(SharedPreferences sharedPreferences, Context context) {
        return sharedPreferences.contains(context.getString(R.string.SELECTED_RECIPE_ID_KEY)) &&
                sharedPreferences.contains(context.getString(R.string.SELECTED_STEP_ID_KEY)) &&
                sharedPreferences.contains(context.getString(R.string.RECIPE_TITLE_KEY)) &&
                sharedPreferences.contains(context.getString(R.string.SELECTED_STEP_TITLE_KEY));
    }
}
