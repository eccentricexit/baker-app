package com.deltabit.bakingapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deltabit.bakingapp.R;
import com.deltabit.bakingapp.model.Step;
import com.google.android.gms.plus.PlusOneButton;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment with a Google +1 button.
 */
public class StepDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "step_id";
    private static final String PLACEHOLDER_URL = "https://s3.amazonaws.com/sitesusa/wp-content/uplo" +
                                                  "ads/sites/212/2016/05/600-x-360-Background-with-t" +
                                                  "he-ingredients-for-a-cake-MaksimVasic-iStock-Thin" +
                                                  "kstock-464617462.jpg";

    @BindView(R.id.imageViewStepVideo) ImageView imageViewStepVideo;
    @BindView(R.id.textViewStepShortDescription) TextView textViewStepShortDescription;
    @BindView(R.id.textViewStepLongDescription) TextView textViewStepLongDescription;

    private BakingAppApplication applicationReference;
    private Context context;

    public StepDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();

        applicationReference = ((BakingAppApplication)context.getApplicationContext());
        if(applicationReference.getSelectedStep()!=null) setupUI(applicationReference.getSelectedStep());

        return view;
    }

    private void setupUI(Step selectedStep) {
        textViewStepShortDescription.setText(selectedStep.getShortDescription());
        textViewStepLongDescription.setText(selectedStep.getDescription());
        if(selectedStep.getThumbnailURL()==null || selectedStep.getThumbnailURL().equals("")) {
            Picasso.with(context)
                    .load(PLACEHOLDER_URL)
                    .into(imageViewStepVideo);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
