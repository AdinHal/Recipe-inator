package com.example.recipeinator.models;

public class MeasuredIngredient extends Ingredient {
    public int amount;
    public String unit;

    public MeasuredIngredient(){
    }

    public MeasuredIngredient(Ingredient ingredient, int amount, String unit){
        super(ingredient.id, ingredient.name);
        this.amount = amount;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", amount, unit, name);
    }
}
