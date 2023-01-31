package org.example.models;

import java.util.ArrayList;

public class OrderModel {

    private ArrayList<String> ingredients;

    public OrderModel(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public OrderModel() {
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
}


