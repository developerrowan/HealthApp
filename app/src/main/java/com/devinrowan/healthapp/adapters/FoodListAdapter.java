package com.devinrowan.healthapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.devinrowan.healthapp.models.Food;
import com.example.healthapp.R;

import java.util.ArrayList;

public class FoodListAdapter extends ArrayAdapter<Food> {
    private Context context;
    private ArrayList<Food> arrayList;
    private TextView txtFoodName, txtFoodCalories;

    public FoodListAdapter(Context context, ArrayList<Food> arrayList) {
        super(context, R.layout.food_list_row, arrayList);
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
        convertView = LayoutInflater.from(context).inflate(R.layout.food_list_row, parent, false);
        txtFoodName = convertView.findViewById(R.id.txtFoodName);
        txtFoodCalories = convertView.findViewById(R.id.txtFoodCalories);

        Food food = arrayList.get(position);

        txtFoodName.setText(String.valueOf(food.getQuantity()) + " " + food.getName().toLowerCase());

        txtFoodCalories.setText(String.valueOf(food.getCalories()) + " " +
                context.getResources().getString(R.string.kcalsEach));

        return convertView;
    }
}
