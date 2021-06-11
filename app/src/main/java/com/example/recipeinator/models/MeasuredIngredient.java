package com.example.recipeinator.models;

public class MeasuredIngredient extends Ingredient {
    public int amount;
    public String unit;

    public MeasuredIngredient(Ingredient ingredient, int amount, String unit){
        super(ingredient.id, ingredient.name);
        this.amount = amount;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "MeasuredIngredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                '}';
    }
}
