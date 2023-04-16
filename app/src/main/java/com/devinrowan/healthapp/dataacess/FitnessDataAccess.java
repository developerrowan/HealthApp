package com.devinrowan.healthapp.dataacess;

import com.devinrowan.healthapp.models.FitnessActivity;

import java.util.ArrayList;
import java.util.Date;

public class FitnessDataAccess {

    private static ArrayList<FitnessActivity> activities = new ArrayList<FitnessActivity>(){{
        add(new FitnessActivity("Running", new Date(), 30, true, 1));
    }};
    private static long id;

    public FitnessDataAccess() {}

    public ArrayList<FitnessActivity> getAllActivities() { return activities; }

    public FitnessActivity getActivityById(long id) {
        for (FitnessActivity a : activities) {
            if(a.getId() == id) {
                return a;
            }
        }

        return null;
    }

    public FitnessActivity insertActivity(FitnessActivity activity) {
        activity.setId(nextId());
        activities.add(activity);

        return activity;
    }

    public FitnessActivity updateActivity(FitnessActivity activity) {
        FitnessActivity originalActivity = getActivityById(activity.getId());

        if(originalActivity == null) return null;

        originalActivity.setDescription(activity.getDescription());
        originalActivity.setDate(activity.getDate());
        originalActivity.setDurationInMinutes(activity.getDurationInMinutes());
        originalActivity.setMeasureInMiles(activity.shouldMeasureInMiles());
        originalActivity.setAmount(activity.getAmount());

        return originalActivity;
    }

    public int deleteActivity(FitnessActivity activity) {
        if (activities.contains((activity))) {
            activities.remove((activity));
            return 1;
        }

        return 0;
    }

    private static long nextId() {
        return ++FitnessDataAccess.id;
    }
}
