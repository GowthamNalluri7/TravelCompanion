package com.example.travelcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity {

    public EditText nameEditText, avgCostEditText, maxDistanceEditText;
    private Slider indoorOutdoorSlider;
    private Spinner cuisineTypeSpinner, indoorOutdoorSpinner;
    private SeekBar ratingSeekBar, ambienceSeekBar;
    private TextView ratingValueTextView, ambienceValueTextView;
    private TimePicker breakfastTimePicker, lunchTimePicker, dinnerTimePicker;
    private Button submitButton;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Elements
        titleTextView = findViewById(R.id.titleTextView);
        nameEditText = findViewById(R.id.nameEditText);
        //System.out.println(nameEditText);
        indoorOutdoorSpinner = findViewById(R.id.indoorOutdoorSpinner);
        cuisineTypeSpinner = findViewById(R.id.cuisineTypeSpinner);
        ratingSeekBar = findViewById(R.id.ratingSeekBar);
        ratingValueTextView = findViewById(R.id.ratingValueTextView);
        avgCostEditText = findViewById(R.id.avgCostEditText);
        maxDistanceEditText = findViewById(R.id.maxDistanceEditText);
        ambienceSeekBar = findViewById(R.id.ambienceSeekBar);
        ambienceValueTextView = findViewById(R.id.ambienceValueTextView);
        breakfastTimePicker = findViewById(R.id.breakfastTimePicker);
        lunchTimePicker = findViewById(R.id.lunchTimePicker);
        dinnerTimePicker = findViewById(R.id.dinnerTimePicker);
        submitButton = findViewById(R.id.submitButton);

        // Title Setup
        titleTextView.setText("Travel Companion - Just For U");

        // Populate Indoor/Outdoor Spinner
        String[] activityPreferences = {"Indoor", "Outdoor"};
        ArrayAdapter<String> activityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, activityPreferences);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        indoorOutdoorSpinner.setAdapter(activityAdapter);

        // Populate Cuisine Type Dropdown
        String[] cuisines = {"QUEBECOISE", "FRENCH", "ITALIAN", "AMERICAN"}; // Replace with dynamic data
        ArrayAdapter<String> cuisineAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cuisines);
        cuisineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisineTypeSpinner.setAdapter(cuisineAdapter);

        // Rating SeekBar Setup
        ratingSeekBar.setMax(5);
        ratingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ratingValueTextView.setText("Rating: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Ambience SeekBar Setup
        ambienceSeekBar.setMax(5);
        ambienceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ambienceValueTextView.setText("Ambience: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Validate Inputs
        setupValidation();

        // Submit Button Listener
        submitButton.setOnClickListener(v -> {
            if (validateInputs()) {
                // Pass Data to the Next Page
//                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                Intent intent = new Intent(MainActivity.this, SuggestionsActivity.class);
                intent.putExtra("userName", nameEditText.getText().toString());
                intent.putExtra("indoorOutdoor", indoorOutdoorSpinner.getSelectedItem().toString().equals("Outdoor")); // True for Outdoor
                intent.putExtra("preferredCuisine", cuisineTypeSpinner.getSelectedItem().toString());
                intent.putExtra("preferredRating", ratingSeekBar.getProgress());
                intent.putExtra("avgCost", Double.parseDouble(avgCostEditText.getText().toString()));
                intent.putExtra("maxDistance", Integer.parseInt(maxDistanceEditText.getText().toString()));
                intent.putExtra("preferredAmbience", ambienceSeekBar.getProgress());
                intent.putExtra("breakfastTime", getTimeFromPicker(breakfastTimePicker));
                intent.putExtra("lunchTime", getTimeFromPicker(lunchTimePicker));
                intent.putExtra("dinnerTime", getTimeFromPicker(dinnerTimePicker));
                startActivity(intent);
            }
        });
    }

    private void setupValidation() {
        avgCostEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty() && Double.parseDouble(s.toString()) < 0.0) {
                    avgCostEditText.setError("Average cost must be greater than or equal to 0.0");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        maxDistanceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty() && Integer.parseInt(s.toString()) < 0) {
                    maxDistanceEditText.setError("Distance must be greater than or equal to 0");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private boolean validateInputs() {
        if (nameEditText.getText().toString().trim().isEmpty()) {
            showToast("Please enter your name.");
            return false;
        }
        if (cuisineTypeSpinner.getSelectedItem() == null) {
            showToast("Please select a preferred cuisine.");
            return false;
        }
        if (avgCostEditText.getText().toString().trim().isEmpty() || Double.parseDouble(avgCostEditText.getText().toString()) < 0.0) {
            showToast("Please enter a valid average cost.");
            return false;
        }
        if (maxDistanceEditText.getText().toString().trim().isEmpty() || Integer.parseInt(maxDistanceEditText.getText().toString()) < 0) {
            showToast("Please enter a valid maximum distance.");
            return false;
        }
        if (!validateTimes()) {
            showToast("Please ensure meal times are in increasing order.");
            return false;
        }
        return true;
    }

    private boolean validateTimes() {
        int breakfastTime = breakfastTimePicker.getHour() * 60 + breakfastTimePicker.getMinute();
        int lunchTime = lunchTimePicker.getHour() * 60 + lunchTimePicker.getMinute();
        int dinnerTime = dinnerTimePicker.getHour() * 60 + dinnerTimePicker.getMinute();
        return breakfastTime < lunchTime && lunchTime < dinnerTime;
    }

    private String getTimeFromPicker(TimePicker timePicker) {
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String amPm = (hour < 12) ? "AM" : "PM";
        if (hour == 0) hour = 12;
        if (hour > 12) hour -= 12;
        return String.format("%02d:%02d %s", hour, minute, amPm);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}