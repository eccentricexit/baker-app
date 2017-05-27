package com.deltabit.bakingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {

    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    public Ingredient(){}

    public Ingredient(String amount, String s) {
        quantity = amount;
        measure = "";
        ingredient = s;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure.toLowerCase();
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public static List<Ingredient> getFakeIngredients(){
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient;

        ingredient = new Ingredient();
        ingredient.setQuantity("350");
        ingredient.setMeasure("G");
        ingredient.setIngredient("Bittersweet chocolate (60-70% cacao)");
        ingredients.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setQuantity("226");
        ingredient.setMeasure("G");
        ingredient.setIngredient("unsalted butter");
        ingredients.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setQuantity("300");
        ingredient.setMeasure("G");
        ingredient.setIngredient("granulated sugar");
        ingredients.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setQuantity("100");
        ingredient.setMeasure("G");
        ingredient.setIngredient("light brown sugar");
        ingredients.add(ingredient);

        return ingredients;
    }
}