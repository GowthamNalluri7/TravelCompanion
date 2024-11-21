package com.example.travelcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText userNameInput;
    private Spinner indoorSpinner, outdoorSpinner, breakfastSpinner, lunchSpinner, dinnerSpinner;
    private TimePicker breakfastTimePicker, lunchTimePicker, dinnerTimePicker;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        userNameInput = findViewById(R.id.userNameInput);
        indoorSpinner = findViewById(R.id.indoorSpinner);
        outdoorSpinner = findViewById(R.id.outdoorSpinner);
        breakfastSpinner = findViewById(R.id.breakfastSpinner);
        lunchSpinner = findViewById(R.id.lunchSpinner);
        dinnerSpinner = findViewById(R.id.dinnerSpinner);
        breakfastTimePicker = findViewById(R.id.breakfastTimePicker);
        lunchTimePicker = findViewById(R.id.lunchTimePicker);
        dinnerTimePicker = findViewById(R.id.dinnerTimePicker);
        submitButton = findViewById(R.id.submitButton);

        // Setup Spinners
        setupSpinner(indoorSpinner, new String[]{"", "Chess", "Table Tennis", "Badminton"});
        setupSpinner(outdoorSpinner, new String[]{"", "Football", "Hiking", "Cycling"});
        setupSpinner(breakfastSpinner, new String[]{"", "Pancakes", "Omelette", "Smoothie"});
        setupSpinner(lunchSpinner, new String[]{"", "Pizza", "Burger", "Salad"});
        setupSpinner(dinnerSpinner, new String[]{"", "Pasta", "Soup", "Steak"});

        // Submit Button Click
        submitButton.setOnClickListener(v -> validateAndProceed());
    }

    private void setupSpinner(Spinner spinner, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void validateAndProceed() {
        String userName = userNameInput.getText().toString();
        String indoorActivity = (String) indoorSpinner.getSelectedItem();
        String outdoorActivity = (String) outdoorSpinner.getSelectedItem();
        String breakfastMeal = (String) breakfastSpinner.getSelectedItem();
        String lunchMeal = (String) lunchSpinner.getSelectedItem();
        String dinnerMeal = (String) dinnerSpinner.getSelectedItem();

        int breakfastHour = breakfastTimePicker.getHour();
        int breakfastMinute = breakfastTimePicker.getMinute();
        int lunchHour = lunchTimePicker.getHour();
        int lunchMinute = lunchTimePicker.getMinute();
        int dinnerHour = dinnerTimePicker.getHour();
        int dinnerMinute = dinnerTimePicker.getMinute();

        // Validate Inputs
        if (userName.isEmpty() || indoorActivity.isEmpty() || outdoorActivity.isEmpty()
                || breakfastMeal.isEmpty() || lunchMeal.isEmpty() || dinnerMeal.isEmpty()) {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isInvalidTimeSequence(breakfastHour, breakfastMinute, lunchHour, lunchMinute, dinnerHour, dinnerMinute)) {
            Toast.makeText(this, "Times must be in sequential order!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Pass Data to Next Activity
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }

    private boolean isInvalidTimeSequence(int bh, int bm, int lh, int lm, int dh, int dm) {
        return (bh > lh || (bh == lh && bm >= lm)) || (lh > dh || (lh == dh && lm >= dm));
    }
}
