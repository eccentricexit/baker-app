package com.deltabit.bakingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deltabit.bakingapp.R;
import com.google.android.gms.plus.PlusOneButton;

/**
 * A fragment with a Google +1 button.
 */
public class StepDetailFragment extends Fragment {

    // The request code must be 0 or greater.
    public StepDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


}
