package com.example.travelcompanion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Spinner indoorSpinner, outdoorSpinner;
    private EditText breakfastTime, lunchTime, dinnerTime;
    private Spinner breakfastSpinner, lunchSpinner, dinnerSpinner;
    private Button submitButton;
    private OkHttpClient client = new OkHttpClient();
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        indoorSpinner = findViewById(R.id.indoorSpinner);
        outdoorSpinner = findViewById(R.id.outdoorSpinner);
        breakfastTime = findViewById(R.id.breakfastTime);
        lunchTime = findViewById(R.id.lunchTime);
        dinnerTime = findViewById(R.id.dinnerTime);
        breakfastSpinner = findViewById(R.id.breakfastSpinner);
        lunchSpinner = findViewById(R.id.lunchSpinner);
        dinnerSpinner = findViewById(R.id.dinnerSpinner);
        submitButton = findViewById(R.id.submitButton);

        setupSpinners();

        submitButton.setOnClickListener(v -> sendPreferences());
    }

    private void setupSpinners() {
        String[] indoorActivities = {"Table Tennis", "Chess", "Badminton"};
        String[] outdoorActivities = {"Cricket", "Football", "Hiking"};
        String[] breakfastMeals = {"Pancakes", "Omelette", "Smoothie"};
        String[] lunchMeals = {"Pizza", "Salad", "Burger"};
        String[] dinnerMeals = {"Steak", "Pasta", "Soup"};

        setSpinnerAdapter(indoorSpinner, indoorActivities);
        setSpinnerAdapter(outdoorSpinner, outdoorActivities);
        setSpinnerAdapter(breakfastSpinner, breakfastMeals);
        setSpinnerAdapter(lunchSpinner, lunchMeals);
        setSpinnerAdapter(dinnerSpinner, dinnerMeals);
    }

    private void setSpinnerAdapter(Spinner spinner, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void sendPreferences() {
        String indoorActivity = indoorSpinner.getSelectedItem().toString();
        String outdoorActivity = outdoorSpinner.getSelectedItem().toString();
        String breakfastTimeInput = breakfastTime.getText().toString();
        String lunchTimeInput = lunchTime.getText().toString();
        String dinnerTimeInput = dinnerTime.getText().toString();
        String breakfastMeal = breakfastSpinner.getSelectedItem().toString();
        String lunchMeal = lunchSpinner.getSelectedItem().toString();
        String dinnerMeal = dinnerSpinner.getSelectedItem().toString();

        Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
        intent.putExtra("indoorActivity", indoorActivity);
        intent.putExtra("outdoorActivity", outdoorActivity);
        intent.putExtra("breakfastTime", breakfastTimeInput);
        intent.putExtra("lunchTime", lunchTimeInput);
        intent.putExtra("dinnerTime", dinnerTimeInput);
        intent.putExtra("breakfastMeal", breakfastMeal);
        intent.putExtra("lunchMeal", lunchMeal);
        intent.putExtra("dinnerMeal", dinnerMeal);
        startActivity(intent);
    }
}
