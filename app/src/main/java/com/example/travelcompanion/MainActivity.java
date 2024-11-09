package com.example.travelcompanion;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
//import okhttp3.Call;
//import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
//import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Spinner indoorSpinner, outdoorSpinner;
    private EditText breakfastTime, lunchTime, dinnerTime;
    private Spinner breakfastSpinner, lunchSpinner, dinnerSpinner;
    private Button submitButton;
    private OkHttpClient client = new OkHttpClient();
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Request location permission and display current location
        requestLocationPermission();

        // Request location permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permissions already granted, proceed with location fetching
            getLocation();
        }

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

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted
            displayCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, fetch and display location
                displayCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void displayCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // If permission is not granted, exit
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                // Display the latitude and longitude in a Toast message
                Toast.makeText(this, "Current Location:\nLatitude: " + latitude + "\nLongitude: " + longitude, Toast.LENGTH_LONG).show();

                // Optionally, save the location
                saveLocation("CurrentLocation", latitude, longitude);
            } else {
                Toast.makeText(this, "Failed to get current location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveLocation(String locationName, double latitude, double longitude) {
        SharedPreferences sharedPreferences = getSharedPreferences("SavedLocations", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(locationName + "_lat", String.valueOf(latitude));
        editor.putString(locationName + "_lon", String.valueOf(longitude));
        editor.apply();
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
