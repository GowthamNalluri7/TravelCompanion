package com.example.travelcompanion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainPageActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView greetingTextView, weatherTextView, locationTextView;
    private RecyclerView restaurantRecyclerView, activityRecyclerView;
    private Button refreshButton, updateLocationButton;

    private Location currentLocation;

    // User preferences
    private String indoorActivity;
    private String outdoorActivity;
    private String breakfastMeal;
    private String lunchMeal;
    private String dinnerMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Initialize views
        greetingTextView = findViewById(R.id.greetingTextView);
        weatherTextView = findViewById(R.id.weatherTextView);
        restaurantRecyclerView = findViewById(R.id.restaurantRecyclerView);
        activityRecyclerView = findViewById(R.id.activityRecyclerView);
        refreshButton = findViewById(R.id.refreshButton);
        updateLocationButton = findViewById(R.id.updateLocationButton);
        locationTextView = findViewById(R.id.locationTextView);

        // Set up location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Get user preferences from Intent
        Intent intent = getIntent();
        indoorActivity = intent.getStringExtra("indoorActivity");
        outdoorActivity = intent.getStringExtra("outdoorActivity");
        breakfastMeal = intent.getStringExtra("breakfastMeal");
        lunchMeal = intent.getStringExtra("lunchMeal");
        dinnerMeal = intent.getStringExtra("dinnerMeal");

        // Set up RecyclerViews for recommendations
        restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load initial data and location updates
        getCurrentLocation();

        // Refresh recommendations on button click
        refreshButton.setOnClickListener(v -> updateUserContext());
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Request location permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        // Request location updates to get current location whenever the app opens
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 60 * 1000); // Update location every 10 minutes

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                if (currentLocation == null || currentLocation.distanceTo(location) > 500) { // Update if user moved more than 500 meters
                    currentLocation = location;
                    updateUserContext();
                    updateLocationText(location);
                }
            }
        }
    };

    private void updateLocationText(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String city = address.getLocality();
                String addressLine = address.getAddressLine(0);
                locationTextView.setText("Current Location: " + (city != null ? city : addressLine));
            } else {
                locationTextView.setText("Location: Unknown");
            }
        } catch (IOException e) {
            e.printStackTrace();
            locationTextView.setText("Location: Unavailable");
        }
    }

    private void updateUserContext() {
        // Update location
        fetchNearbyRecommendations(currentLocation);

        // Update time-based greeting
        updateTimeBasedGreeting();

        // Update weather-based recommendation
        updateWeatherInfo();
    }

    private void fetchNearbyRecommendations(Location location) {
        List<String> restaurantRecommendations = new ArrayList<>();
        List<String> activityRecommendations = new ArrayList<>();

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if (hour >= 6 && hour < 10) {
            restaurantRecommendations.add("Sunrise Cafe - Serving " + breakfastMeal);
            restaurantRecommendations.add("Morning Delight - Cozy breakfast spot");
            restaurantRecommendations.add("Brunch Hub - Ideal for " + breakfastMeal);
            activityRecommendations.add("Yoga Studio - Relaxing morning activity");
            activityRecommendations.add("Early Bird Gym - Start your day right");
            activityRecommendations.add("Botanical Gardens - Morning stroll");
        } else if (hour >= 12 && hour < 14) {
            restaurantRecommendations.add("Spicy Biryani House - Great for " + lunchMeal);
            restaurantRecommendations.add("Rice Bowl - Offers fried rice options");
            restaurantRecommendations.add("Asian Delights - Known for rice dishes");
            activityRecommendations.add("Central Park - Afternoon picnic");
            activityRecommendations.add("City Sports Complex - Play " + outdoorActivity);
            activityRecommendations.add("Museum Visit - Afternoon exploration");
        } else if (hour >= 16 && hour < 18) {
            restaurantRecommendations.add("Snack Shack - Perfect for snacks");
            restaurantRecommendations.add("Coffee Corner - Afternoon coffee spot");
            restaurantRecommendations.add("Bakery Bliss - Fresh pastries");
            activityRecommendations.add("Indoor Game Zone - Play " + indoorActivity);
            activityRecommendations.add("Art Studio - Evening art sessions");
            activityRecommendations.add("Shopping Mall - Evening shopping");
        } else if (hour >= 18 && hour < 22) {
            restaurantRecommendations.add("Dinner Hub - Best for " + dinnerMeal);
            restaurantRecommendations.add("Steakhouse - Known for gourmet dishes");
            restaurantRecommendations.add("Fusion Cuisine - Ideal for rice dishes");
            activityRecommendations.add("Fitness Center - Evening workout");
            activityRecommendations.add("Bowling Alley - Fun indoor games");
            activityRecommendations.add("Theater - Evening shows");
        }

        RecommendationAdapter restaurantAdapter = new RecommendationAdapter(restaurantRecommendations);
        RecommendationAdapter activityAdapter = new RecommendationAdapter(activityRecommendations);

        restaurantRecyclerView.setAdapter(restaurantAdapter);
        activityRecyclerView.setAdapter(activityAdapter);
    }

    private void updateTimeBasedGreeting() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String greeting = (hour >= 5 && hour < 12) ? "Good Morning" :
                (hour >= 12 && hour < 18) ? "Good Afternoon" : "Good Evening";
        greetingTextView.setText(greeting + ", User!");
    }

    private void updateWeatherInfo() {
        weatherTextView.setText("Weather: Sunny, 25°C");
    }

    // Handle location permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission is required for recommendations", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
