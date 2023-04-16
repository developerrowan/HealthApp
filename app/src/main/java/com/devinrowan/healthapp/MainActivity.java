package com.devinrowan.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.devinrowan.healthapp.adapters.MealListAdapter;
import com.devinrowan.healthapp.dataacess.MealDataAccess;
import com.devinrowan.healthapp.models.MealEntry;
import com.example.healthapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lsMeals;
    private FloatingActionButton btnAdd;
    private ArrayList<MealEntry> allMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lsMeals = findViewById(R.id.lsMeals);
        allMeals = new MealDataAccess().getAllMeals();
        lsMeals.setAdapter(new MealListAdapter(this, allMeals));
        lsMeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MealEntry meal = allMeals.get(position);

                Intent intent = new Intent(MainActivity.this, MealDetailsActivity.class);
                intent.putExtra(MealDetailsActivity.EXTRA_MEAL_ID, meal.getId());

                startActivity(intent);
            }
        });

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MealDetailsActivity.class);

                // -1 signifies creation
                intent.putExtra(MealDetailsActivity.EXTRA_MEAL_ID, -1);

                startActivity(intent);
            }
        });
    }
}