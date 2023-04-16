package com.devinrowan.healthapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.devinrowan.healthapp.models.Food;
import com.devinrowan.healthapp.models.MealEntry;
import com.example.healthapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MealListAdapter extends ArrayAdapter<MealEntry> {
    private Context context;
    private ArrayList<MealEntry> arrayList;
    private TextView date, totalItems, totalCalories;

    public MealListAdapter(Context context, ArrayList<MealEntry> arrayList) {
        super(context, R.layout.meal_list_row, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.meal_list_row, parent, false);
        date = convertView.findViewById(R.id.txtFoodName);
        totalItems = convertView.findViewById(R.id.totalItems);
        totalCalories = convertView.findViewById(R.id.totalCalories);

        MealEntry meal = arrayList.get(position);
        int tCalories = 0;
        int tItems = 0;

        for (Food f : meal.getItems()) {
            tCalories += f.getCalories() * f.getQuantity();
            tItems += f.getQuantity();
        }

        date.setText(meal.getDescription() + " - " + new SimpleDateFormat("EEE, MMMM d, yyyy").format(meal.getDate()));
        totalItems.setText(String.valueOf(tItems));
        totalCalories.setText(String.valueOf(tCalories) + " kcals");
        return convertView;
    }
}
