package com.deltabit.bakingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Step {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("videoURL")
    @Expose
    private String videoURL;
    @SerializedName("thumbnailURL")
    @Expose
    private String thumbnailURL;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public static List<Step> getFakeSteps(){
        List<Step> steps = new ArrayList<>();
        Step step;

        step = new Step();
        step.setId(1);
        step.setShortDescription("Starting prep");
        step.setDescription("1. Preheat the oven to 350\\u00b0F. Butter a 9\\\" deep dish pie pan.");
        step.setVideoURL("");
        step.setThumbnailURL("");
        steps.add(step);

        step = new Step();
        step.setId(2);
        step.setShortDescription("Prep the cookie crust.");
        step.setDescription("2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.");
        step.setVideoURL("");
        step.setThumbnailURL("");
        steps.add(step);

        step = new Step();
        step.setId(3);
        step.setShortDescription("Press the crust into baking form.");
        step.setDescription("3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.");
        step.setVideoURL("");
        step.setThumbnailURL("");
        steps.add(step);


        return steps;
    }

}

