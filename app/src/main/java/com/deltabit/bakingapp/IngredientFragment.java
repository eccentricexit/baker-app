package com.deltabit.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class IngredientFragment extends Fragment {


    private BakingApplication applicationReference;
    public IngredientFragment() {  }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);
        applicationReference = ((BakingApplication)getActivity().getApplicationContext());

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(
                    new IngredientRecyclerViewAdapter(
                            applicationReference
                                    .getSelectedRecipe()
                                    .getIngredients()
                    )
            );
        }
        return view;
    }




}
