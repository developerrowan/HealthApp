package com.devinrowan.healthapp.dataacess;

import com.devinrowan.healthapp.models.Food;
import com.devinrowan.healthapp.models.MealEntry;

import java.util.ArrayList;
import java.util.Date;

public class MealDataAccess {

    private static ArrayList<MealEntry> meals = new ArrayList<MealEntry>(){{
        add(new MealEntry(1,"Dinner", new Date(), new ArrayList<Food>(){{
            add(new Food("Apple", 1, 1));
            add(new Food("Chicken", 1, 1));
            add(new Food("Soda", 1, 1));
        }}));
    }};
    private static long id = 1;

    public MealDataAccess() {}

    public ArrayList<MealEntry> getAllMeals() { return meals; }

    public MealEntry getMealById(long id) {
        for (MealEntry m : meals) {
            if(m.getId() == id) {
                return m;
            }
        }

        return null;
    }

    public MealEntry insertMeal(MealEntry meal) {
        meal.setId(nextId());
        meals.add(meal);

        return meal;
    }

    public MealEntry updateMeal(MealEntry meal) {
        MealEntry originalMeal = getMealById(meal.getId());

        if(originalMeal == null) return null;

        originalMeal.setDescription(meal.getDescription());
        originalMeal.setDate(meal.getDate());
        originalMeal.setItems(meal.getItems());

        return originalMeal;
    }

    public int deleteMeal(MealEntry meal) {
        if (meals.contains((meal))) {
            meals.remove((meal));
            return 1;
        }

        return 0;
    }

    private static long nextId() {
        return ++MealDataAccess.id;
    }
}
