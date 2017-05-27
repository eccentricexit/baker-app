package com.deltabit.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.deltabit.bakingapp.model.Recipe;
import com.deltabit.bakingapp.model.Step;
import com.deltabit.bakingapp.widget.BakingRemoteViewsProvider;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListActivity extends AppCompatActivity {

    private static final String INGREDIENTS = "ingredients";
    private static final String LOG_TAG = StepListActivity.class.getSimpleName();

    @BindView(R.id.imageviewRecipe) ImageView imageViewRecipe;
    @BindView(R.id.recyclerViewSteps) RecyclerView recyclerViewSteps;
    @BindView(R.id.buttonIngredients) Button buttonIngredients;

    private Recipe selectedRecipe;
    private boolean mTwoPane;
    private BakingApplication applicationReference;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);
        ButterKnife.bind(this);
        context = this;

        if (findViewById(R.id.step_detail_container) != null)
            mTwoPane = true;


        applicationReference = ((BakingApplication)getApplicationContext());
        selectedRecipe = applicationReference.getSelectedRecipe();
        if(selectedRecipe!=null) setupUI(selectedRecipe);

        applicationReference.setSelectedStepId(0);

        if (mTwoPane) {
            StepDetailFragment fragment = new StepDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, fragment)
                    .commit();
        }

    }

    private void setupUI(Recipe selectedRecipe) {
        assert selectedRecipe!=null;

        if(selectedRecipe.getImage()!=null && !selectedRecipe.getImage().equals("")) {
            Picasso.with(this)
                    .load(selectedRecipe.getImage())
                    .into(imageViewRecipe);
        }

        recyclerViewSteps.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSteps.setAdapter(new SimpleItemRecyclerViewAdapter(selectedRecipe.getSteps()));


        buttonIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTwoPane) {
                    StepDetailFragment fragment = new StepDetailFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.step_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(context, IngredientActivity.class);
                    context.startActivity(intent);
                }
            }
        });

        saveStepToSharedPreferences(0,selectedRecipe.getSteps().get(0).getShortDescription());


    }

    public void saveStepToSharedPreferences(int selectedStepId,String selectedStepTitle){
        SharedPreferences.Editor editor = context.getSharedPreferences(
                context.getString(R.string.SHARED_PREFERENCES_KEY),
                MODE_PRIVATE
        ).edit();

        editor.putInt(context.getString(R.string.SELECTED_STEP_ID_KEY), selectedStepId);
        editor.putString(context.getString(R.string.SELECTED_STEP_TITLE_KEY),selectedStepTitle);

        editor.commit();
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Step> steps;

        public SimpleItemRecyclerViewAdapter(List<Step> steps) {
            this.steps = steps;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_step_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.step = steps.get(position);
            holder.textViewItemId.setText(holder.step.getId()+1+"");
            holder.textViewItemContent.setText(holder.step.getShortDescription());

            holder.viewHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    applicationReference.setSelectedStepId(position);
                    saveStepToSharedPreferences(position,holder.step.getShortDescription());
                    requestWidgetUpdate();

                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(StepDetailFragment.ARG_ITEM_ID, holder.step.getShortDescription());
                        StepDetailFragment fragment = new StepDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.step_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, StepDetailActivity.class);
                        intent.putExtra(StepDetailFragment.ARG_ITEM_ID, holder.step.getShortDescription());

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return steps.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View viewHolder;
            public final TextView textViewItemId;
            public final TextView textViewItemContent;
            public Step step;

            public ViewHolder(View view) {
                super(view);
                viewHolder = view;
                textViewItemId = (TextView) view.findViewById(R.id.item_step_id);
                textViewItemContent = (TextView) view.findViewById(R.id.item_step_shortDescription);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + textViewItemContent.getText() + "'";
            }
        }
    }

    private void requestWidgetUpdate(){
        Intent intent = new Intent(this, BakingRemoteViewsProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), BakingRemoteViewsProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);

        Log.d(LOG_TAG,"Sending update broadcast");
    }
}
