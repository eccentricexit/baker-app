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
        updateSelectedStepInfo();

        return view;
    }

    private void setupUI(Step selectedStep) {
        textViewStepShortDescription.setText(selectedStep.getShortDescription());
        textViewStepLongDescription.setText(selectedStep.getDescription());

        if(selectedStep.getThumbnailURL()==null || selectedStep.getThumbnailURL().equals(""))
            selectedStep.setThumbnailURL(Util.NO_PREVIEW_AVAILABLE);

        Picasso.with(context)
                .load(selectedStep.getThumbnailURL())
                .placeholder(R.drawable.nopreviewavailable)
                .error(R.drawable.nopreviewavailable)
                .into(imageViewStepVideo);
    }

    public void updateSelectedStepInfo(){
        setupUI(
                applicationReference
                        .getSteps()
                        .get(applicationReference.getSelectedStep())
        );
    }


    @Override
    public void onResume() {
        super.onResume();
    }


}
