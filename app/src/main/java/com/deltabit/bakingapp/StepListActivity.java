package com.deltabit.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deltabit.bakingapp.model.Recipe;
import com.deltabit.bakingapp.model.Step;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListActivity extends AppCompatActivity {

    @BindView(R.id.imageviewRecipe) ImageView imageViewRecipe;
    @BindView(R.id.recyclerViewSteps) RecyclerView recyclerViewSteps;

    private Recipe selectedRecipe;
    private boolean mTwoPane;
    private BakingAppApplication applicationReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);
        ButterKnife.bind(this);

        applicationReference = ((BakingAppApplication)getApplicationContext());
        selectedRecipe = applicationReference.getSelectedRecipe();
        if(selectedRecipe!=null) setupUI(selectedRecipe);

    }

    private void setupUI(Recipe selectedRecipe) {
        assert selectedRecipe!=null;

        Picasso.with(this)
                .load(selectedRecipe.getImage())
                .into(imageViewRecipe);

        recyclerViewSteps.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSteps.setAdapter(new SimpleItemRecyclerViewAdapter(selectedRecipe.getSteps()));
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
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.step = steps.get(position);
            holder.textViewItemId.setText(holder.step.getId().toString());
            holder.textViewItemContent.setText(holder.step.getShortDescription());

            holder.viewHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    applicationReference.setSelectedStep(holder.step);

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
}
