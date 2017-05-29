package com.deltabit.bakingapp;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deltabit.bakingapp.model.Step;
import com.deltabit.bakingapp.util.Util;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment with a Google +1 button.
 */
public class StepDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "step_id";
    private static final String LOG_TAG = StepDetailFragment.class.getSimpleName();

    @BindView(R.id.imageViewStepVideo) ImageView imageViewStepVideo;
    @BindView(R.id.textViewStepShortDescription) TextView textViewStepShortDescription;
    @BindView(R.id.textViewStepLongDescription) TextView textViewStepLongDescription;
    @BindView(R.id.simpleExoPlayerView) SimpleExoPlayerView simpleExoPlayerView;

    private BakingApplication applicationReference;
    private Context context;
    private SimpleExoPlayer player;

    public StepDetailFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();
        applicationReference = ((BakingApplication) context.getApplicationContext());

        updateSelectedStepInfo(applicationReference
                .getSteps().get(applicationReference.getSelectedStepId())
        );
        return view;
    }

    private void setupUI(Step selectedStep) {
        textViewStepShortDescription.setText(selectedStep.getShortDescription());
        textViewStepLongDescription.setText(selectedStep.getDescription());

        if(selectedStep.getVideoURL().equals(""))
            imageViewStepVideo.setVisibility(View.VISIBLE);
        else
            imageViewStepVideo.setVisibility(View.GONE);

        if (selectedStep.getThumbnailURL() == null || selectedStep.getThumbnailURL().equals(""))
            selectedStep.setThumbnailURL(Util.NO_PREVIEW_AVAILABLE);

        Picasso.with(context)
                .load(selectedStep.getThumbnailURL())
                .placeholder(R.drawable.nopreviewavailable)
                .error(R.drawable.nopreviewavailable)
                .into(imageViewStepVideo);

    }

    public void updateSelectedStepInfo(Step selectedStep) {
        Log.d(LOG_TAG,applicationReference.getSelectedRecipe().getName());
        Log.d(LOG_TAG, selectedStep.getShortDescription());

        setupUI(selectedStep);
        setupExoPlayer(selectedStep);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(player!=null) {
            player.release();
        }
    }

    private void setupExoPlayer(Step selectedStep) {
        if (player != null)
            player.release();

        if (selectedStep.getVideoURL() != null && !selectedStep.getVideoURL().equals("")) {
            simpleExoPlayerView.setVisibility(View.VISIBLE);

            // 1. Create a default TrackSelector
            Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            // 2. Create the player
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

            simpleExoPlayerView.setPlayer(player);


            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, "BakingApp");
            // Produces Extractor instances for parsing the media data.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            // This is the MediaSource representing the media to be played.
            Uri selectedVideoUri = Uri.parse(selectedStep.getVideoURL());
            MediaSource videoSource = new ExtractorMediaSource(selectedVideoUri,
                    dataSourceFactory, extractorsFactory, null, null);
            // Prepare the player with the source.
            player.prepare(videoSource);
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
        }


    }

}
