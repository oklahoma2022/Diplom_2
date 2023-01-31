package org.example.service;

import org.example.api.OrderRestClient;
import org.example.models.OrderModel;

import java.util.ArrayList;

public class OrderService {
    OrderRestClient orderRestClient;

    public OrderService() {
        orderRestClient = new OrderRestClient();
    }

    public OrderModel createOrderWithIngredients(int countIngredients) {
        ArrayList<String> ingredients = new ArrayList<>();

        for (int i = 0; i <= countIngredients; i++) {
            String ingredient = orderRestClient.getIngredient(countIngredients);

            if (ingredient == null) {
                throw new RuntimeException("Указано слишком большое количемство инградиентов");
            }

            ingredients.add(orderRestClient.getIngredient(countIngredients));
        }

        return new OrderModel(ingredients);
    }

    public OrderModel createOrderWithCustomIngredient(String ingredientID) {
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add(ingredientID);

        return new OrderModel(ingredients);
    }
}
