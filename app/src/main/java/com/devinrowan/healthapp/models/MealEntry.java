package com.devinrowan.healthapp.models;

import java.util.ArrayList;
import java.util.Date;

public class MealEntry implements Cloneable {

    private long id;
    private String description;
    private Date date;
    private ArrayList<Food> items;

    public MealEntry(String description, Date date, ArrayList<Food> items) {
        this.description = description;
        this.date = date;
        this.items = items;
    }

    public MealEntry(long id, String description, Date date, ArrayList<Food> items) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.items = items;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Food> getItems() {
        return items;
    }

    public void setItems(ArrayList<Food> items) {
        this.items = items;
    }

    public MealEntry clone() {
        ArrayList<Food> temp = new ArrayList();

        for (Food f : getItems()) {
            temp.add(f);
        }

        return new MealEntry(getId(), getDescription(), getDate(), temp);
    }
}