package com.example.travelcompanion;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.widget.Toast;


public class MainPageActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private TextView greetingTextView, weatherTextView;
    private RecyclerView recommendationRecyclerView;
    private Button refreshButton;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Initialize views
        greetingTextView = findViewById(R.id.greetingTextView);
        weatherTextView = findViewById(R.id.weatherTextView);
        recommendationRecyclerView = findViewById(R.id.recyclerview);
        refreshButton = findViewById(R.id.refreshButton);

        // Set up location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Set up RecyclerView for recommendations
        recommendationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load initial data
        updateUserContext();

        // Refresh recommendations on button click (for demo purposes)
        refreshButton.setOnClickListener(v -> updateUserContext());
    }

    private void updateUserContext() {
        // Update location
        updateLocation();

        // Update time-based greeting
        updateTimeBasedGreeting();

        // Update weather-based recommendation
        updateWeatherInfo();

        // Fetch and display recommendations
        updateRecommendations();
    }

    private void updateLocation() {
        // Check for location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Request location permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        // If permission is granted, get the location
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                fetchNearbyRecommendations(location);
            }
        });
    }
    private void fetchNearbyRecommendations(Location location) {
        // Placeholder for actual API call. Add code to fetch from Google Places, for example.
        List<String> sampleRecommendations = new ArrayList<>();
        sampleRecommendations.add("Cafe XYZ");
        sampleRecommendations.add("Central Park");
        sampleRecommendations.add("Historic Museum");

        // Update RecyclerView adapter with recommendations
        RecommendationAdapter adapter = new RecommendationAdapter(sampleRecommendations);
        recommendationRecyclerView.setAdapter(adapter);
    }

    private void updateTimeBasedGreeting() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String greeting;
        if (hour >= 5 && hour < 12) {
            greeting = "Good Morning";
        } else if (hour >= 12 && hour < 18) {
            greeting = "Good Afternoon";
        } else {
            greeting = "Good Evening";
        }
        greetingTextView.setText(greeting + ", User!");
    }

    private void updateWeatherInfo() {
        // Placeholder: Normally fetch from a weather API
        weatherTextView.setText("Weather: Sunny, 25°C");
        // Customize based on weather (indoor or outdoor recommendations)
    }

    private void updateRecommendations() {
        // Combine preferences, location, time, and weather data to fetch personalized recommendations
    }

    // Handle location permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Call the super method here

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with updating location
                updateLocation();
            } else {
                // Permission denied - handle appropriately
                Toast.makeText(this, "Location permission is required for recommendations", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
