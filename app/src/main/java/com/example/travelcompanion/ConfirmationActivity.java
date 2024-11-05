package com.example.travelcompanion;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        // Retrieve data from intent
        String indoorActivity = getIntent().getStringExtra("indoorActivity");
        String outdoorActivity = getIntent().getStringExtra("outdoorActivity");
        String breakfastTime = getIntent().getStringExtra("breakfastTime");
        String lunchTime = getIntent().getStringExtra("lunchTime");
        String dinnerTime = getIntent().getStringExtra("dinnerTime");
        String breakfastMeal = getIntent().getStringExtra("breakfastMeal");
        String lunchMeal = getIntent().getStringExtra("lunchMeal");
        String dinnerMeal = getIntent().getStringExtra("dinnerMeal");

        // Display data
        ((TextView) findViewById(R.id.displayIndoorActivity)).setText(indoorActivity);
        ((TextView) findViewById(R.id.displayOutdoorActivity)).setText(outdoorActivity);
        ((TextView) findViewById(R.id.displayBreakfastTime)).setText("Breakfast: " + breakfastTime);
        ((TextView) findViewById(R.id.displayLunchTime)).setText("Lunch: " + lunchTime);
        ((TextView) findViewById(R.id.displayDinnerTime)).setText("Dinner: " + dinnerTime);
        ((TextView) findViewById(R.id.displayBreakfastMeal)).setText("Breakfast Meal: " + breakfastMeal);
        ((TextView) findViewById(R.id.displayLunchMeal)).setText("Lunch Meal: " + lunchMeal);
        ((TextView) findViewById(R.id.displayDinnerMeal)).setText("Dinner Meal: " + dinnerMeal);
    }
}