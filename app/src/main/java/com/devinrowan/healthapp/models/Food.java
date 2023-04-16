package com.devinrowan.healthapp.models;

import java.util.Locale;

public class Food {
    String name;
    int quantity;
    int calories;

    public Food(String name, int quantity, int calories) {
        this.name = name;
        this.quantity = quantity;
        this.calories = calories;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return String.format("%d %s: %d calories each", quantity, name.toLowerCase(), calories);
    }
}
