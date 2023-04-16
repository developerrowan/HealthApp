package com.devinrowan.healthapp.models;

import java.util.Date;

public class FitnessActivity {
    private long id;
    private String description;
    private Date date;
    private int durationInMinutes;

    // If this is true, the amount will be considered to be miles. Else, it'll be reps.
    private boolean measureInMiles;
    private double amount;

    public FitnessActivity(String description, Date date, int durationInMinutes, boolean measureInMiles, double amount) {
        this.description = description;
        this.date = date;
        this.durationInMinutes = durationInMinutes;
        this.measureInMiles = measureInMiles;
        this.amount = amount;
    }

    public FitnessActivity(long id, String description, Date date, int durationInMinutes, boolean measureInMiles, double amount) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.durationInMinutes = durationInMinutes;
        this.measureInMiles = measureInMiles;
        this.amount = amount;
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

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public boolean shouldMeasureInMiles() {
        return measureInMiles;
    }

    public void setMeasureInMiles(boolean measureInMiles) {
        this.measureInMiles = measureInMiles;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
