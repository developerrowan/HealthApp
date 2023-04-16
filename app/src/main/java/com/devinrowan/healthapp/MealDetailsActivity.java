package com.devinrowan.healthapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.Validation;
import com.devinrowan.healthapp.adapters.FoodListAdapter;
import com.devinrowan.healthapp.dataacess.MealDataAccess;
import com.devinrowan.healthapp.models.Food;
import com.devinrowan.healthapp.models.MealEntry;
import com.example.healthapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MealDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MEAL_ID = "mealId";

    MealEntry copyMeal;
    EditText txtDescription, txtDate;
    Button btnSave, btnCancel, btnAdd;
    ImageButton btnDelete, btnPickDate;
    ListView foodList;
    MealDataAccess da;

    boolean isDirty = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        da = new MealDataAccess();

        txtDescription = findViewById(R.id.txtDescription);
        txtDate = findViewById(R.id.txtDate);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnAdd = findViewById(R.id.btnFoodAdd);
        btnDelete = findViewById(R.id.btnDelete);
        btnPickDate = findViewById(R.id.btnPickDate);
        foodList = findViewById(R.id.lsFood);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate())
                    return;

                copyMeal.setDescription(txtDescription.getText().toString());
                try {
                    copyMeal.setDate(new SimpleDateFormat("M/d/yyyy").parse(txtDate.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long id = getIntent().getLongExtra(EXTRA_MEAL_ID, 0);

                if (id > 0)
                    da.updateMeal(copyMeal);
                else
                    da.insertMeal(copyMeal);

                leave();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmUnsavedChanges();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MealDetailsActivity.this)
                        .setTitle(R.string.areYouSureTitle)
                        .setMessage(R.string.areYouSureMeal)
                        .setCancelable(true)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MealEntry meal = da.getMealById(copyMeal.getId());

                                if (meal != null) {
                                    if(da.deleteMeal(meal) == 1) {
                                        leave();
                                    }
                                }
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            Date today = new Date();

            // oH no tHAtS DEpreCATeD screw you android, I am NOT using java's calendar class.
            int year = today.getYear() + 1900;
            int month = today.getMonth();
            int day = today.getDate();


            @Override
            public void onClick(View view) {
                DatePickerDialog dpDialog = new DatePickerDialog(MealDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        txtDate.setText((m + 1) + "/" + d + "/" + y);
                    }
                }, year, month, day);

                dpDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dpDialog.show();
            }
        });

        foodList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                new AlertDialog.Builder(MealDetailsActivity.this)
                        .setTitle(R.string.areYouSureTitle)
                        .setMessage(R.string.areYouSureFood)
                        .setCancelable(true)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int ii) {
                                ArrayList<Food> items = copyMeal.getItems();

                                Food f = items.get(i);
                                items.remove(f);
                                foodList.invalidateViews();

                                makeDirty();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .create()
                        .show();

                return true;
            }
        });

        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                addFoodDialog(i);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodDialog(-1);
            }
        });

        long id = getIntent().getLongExtra(EXTRA_MEAL_ID, 0);

        if (id > 0)
            copyMeal = da.getMealById(id).clone();
        else
            copyMeal = new MealEntry("", new Date(), new ArrayList<Food>());

        foodList.setAdapter(new FoodListAdapter(this, copyMeal.getItems()));

        populateUI();
    }

    private void addFoodDialog(int foodId) {
        boolean isUpdate = foodId >= 0;

        int title = isUpdate ? R.string.updateFoodTitle : R.string.addFoodTitle;
        int action = isUpdate ? R.string.update : R.string.add;

        AlertDialog.Builder builder = new AlertDialog.Builder(MealDetailsActivity.this);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setPositiveButton(action, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setView(R.layout.dialog_add_food);

        AlertDialog dialog = builder.create();

        dialog.show();

        EditText txtName, txtQuantity, txtCalories;

        txtName = dialog.findViewById(R.id.txtName);
        txtQuantity = dialog.findViewById(R.id.numQuantity);
        txtCalories = dialog.findViewById(R.id.numCalories);

        if (isUpdate) {
            Food f = copyMeal.getItems().get(foodId);

            txtName.setText(f.getName());
            txtQuantity.setText(String.valueOf(f.getQuantity()));
            txtCalories.setText(String.valueOf(f.getCalories()));
        }

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFoodDialog(dialog))
                    return;

                String name = txtName.getText().toString();
                int quantity = Integer.parseInt(txtQuantity.getText().toString());
                int calories = Integer.parseInt(txtCalories.getText().toString());

                if (isUpdate) {
                    Food f = copyMeal.getItems().get(foodId);

                    f.setName(name);
                    f.setQuantity(quantity);
                    f.setCalories(calories);

                } else {
                    copyMeal.getItems().add(new Food(name, quantity, calories));
                }

                foodList.invalidateViews();
                dialog.dismiss();

                makeDirty();
            }
        });
    }

    private void makeDirty() {
        isDirty = true;

        btnSave.setEnabled(true);
    }

    private void populateUI() {
        txtDescription.setText(copyMeal.getDescription());
        txtDate.setText(new SimpleDateFormat("M/d/yyyy").format(copyMeal.getDate()));

        txtDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                makeDirty();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                makeDirty();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void confirmUnsavedChanges() {
        if (isDirty) {
            new AlertDialog.Builder(this)
            .setTitle(R.string.areYouSureTitle)
            .setMessage(R.string.areYouSure)
            .setCancelable(true)
            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    leave();
                }
            })
            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            })
            .create()
            .show();
        } else {
            leave();
        }
    }

    private void leave() {
        Intent i = new Intent(MealDetailsActivity.this, MainActivity.class);
        startActivity(i);
    }

    private boolean validate() {
        Validation validation = new Validation();
        boolean isValid = true;

        String description = txtDescription.getText().toString();
        String date = txtDate.getText().toString();
        int numberOfFood = copyMeal.getItems().size();

        if (!validation.isLengthWithinBounds(description, 1, 20)) {
            txtDescription.setError(
                    String.format(getResources().getString(R.string.requiredTextRange),
                            getResources().getString(R.string.description),
                            1,
                            20));
            isValid = false;
        }

        if (date.isEmpty()) {
            txtDate.setError(
                    String.format(getResources().getString(R.string.isRequired),
                            getResources().getString(R.string.date)));
            isValid = false;
        }
        else if (!validation.isValidDateFormat(date, "M/d/yyyy")) {
            txtDate.setError(getResources().getString(R.string.invalidDateFormat));
            isValid = false;
        }

        return isValid;
    }

    private boolean validateFoodDialog(AlertDialog dialog) {
        Validation validation = new Validation();
        boolean isValid = true;

        EditText txtName, txtQuantity, txtCalories;

        txtName = dialog.findViewById(R.id.txtName);
        txtQuantity = dialog.findViewById(R.id.numQuantity);
        txtCalories = dialog.findViewById(R.id.numCalories);

        String name = txtName.getText().toString();
        String quantity = txtQuantity.getText().toString();
        String calories = txtCalories.getText().toString();

        if (!validation.isLengthWithinBounds(name, 1, 20)) {
            txtName.setError(
                    String.format(getResources().getString(R.string.requiredTextRange),
                            getResources().getString(R.string.name),
                            1,
                            20));
            isValid = false;
        }

        if (quantity.isEmpty() || !validation.isWithinRange(Integer.parseInt(quantity), 1, 50)) {
            txtQuantity.setError(
                    String.format(getResources().getString(R.string.requiredRange),
                            getResources().getString(R.string.quantity),
                            1,
                            50));
            isValid = false;
        }

        if (calories.isEmpty() || !validation.isWithinRange(Integer.parseInt(calories), 1, 3000)) {
            txtCalories.setError(
                    String.format(getResources().getString(R.string.requiredRange),
                            getResources().getString(R.string.calories),
                            1,
                            3000));
            isValid = false;
        }

        return isValid;
    }
}