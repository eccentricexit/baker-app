package com.deltabit.bakingapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = null;
    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    private void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    private void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    private void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static List<Recipe> getFakeRecipies(){
        List<Recipe> recipies = new ArrayList<>();
        Recipe recipe;

        recipe = new Recipe();
        recipe.setId(1);
        recipe.setName("Nutella Pie");
        recipe.setServings(8);
        recipe.setImage("http://d6h7vs5ykbiug.cloudfront.net/wp-content/uploads/2012/08/Nutella-No-Bake-Cookies-Recipe-7.jpg");
        recipe.setIngredients(Ingredient.getFakeIngredients());
        recipe.setSteps(Step.getFakeSteps());
        recipies.add(recipe);

        recipe = new Recipe();
        recipe.setId(2);
        recipe.setName("Brownies");
        recipe.setServings(8);
        recipe.setImage("https://www-tc.pbs.org/food/files/2013/02/katharine-hepburn-brownies-1.jpg");
        recipe.setIngredients(Ingredient.getFakeIngredients());
        recipe.setSteps(Step.getFakeSteps());
        recipies.add(recipe);

        recipe = new Recipe();
        recipe.setId(3);
        recipe.setName("Yellow Cake");
        recipe.setServings(8);
        recipe.setImage("https://s3.amazonaws.com/foodfornet/wp-content/uploads/2016/02/Pamelas-Gluten-Free-Yellow-Cake.jpg");
        recipe.setIngredients(Ingredient.getFakeIngredients());
        recipe.setSteps(Step.getFakeSteps());
        recipies.add(recipe);

        recipe = new Recipe();
        recipe.setId(4);
        recipe.setName("Cheesecake");
        recipe.setServings(8);
        recipe.setImage("http://goodtoknow.media.ipcdigital.co.uk/111/00001205f/b414_orh412w625/Mary-Berrys-lemon-and-lime-cheesecake.jpg");
        recipe.setIngredients(Ingredient.getFakeIngredients());
        recipe.setSteps(Step.getFakeSteps());
        recipies.add(recipe);

        return recipies;
    }

    public static List<Recipe> getRecipiesFromJson(String json){
        Recipe[] recipies = new Gson().fromJson(json,Recipe[].class);

        return Arrays.asList(recipies);
    }
}