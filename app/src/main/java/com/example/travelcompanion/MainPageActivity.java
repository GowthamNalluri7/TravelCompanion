package com.example.travelcompanion;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import project.Manager;
import project.Activity;
import project.Restaurant;

public class MainPageActivity extends AppCompatActivity {

    private static final String WEATHER_API_KEY = "ef10fcced2ae0f630a729efe0bf52d2c";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private FusedLocationProviderClient fusedLocationClient;
    private TextView greetingTextView, weatherTextView, locationTextView;
    private RecyclerView restaurantRecyclerView, activityRecyclerView;
    private Button refreshButton, updateLocationButton;

    private Location currentLocation;
    private String currentWeather = "SUNNY"; // Default weather
    private String currentLocationName = "Mile End"; // Default location

    private Manager manager;
    private List<Activity> activityRecommendations;
    private List<Restaurant> restaurantRecommendations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Initialize views
        greetingTextView = findViewById(R.id.greetingTextView);
        weatherTextView = findViewById(R.id.weatherTextView);
        locationTextView = findViewById(R.id.locationTextView);
        restaurantRecyclerView = findViewById(R.id.restaurantRecyclerView);
        activityRecyclerView = findViewById(R.id.activityRecyclerView);
        refreshButton = findViewById(R.id.refreshButton);
        updateLocationButton = findViewById(R.id.updateLocationButton);

        // Set up RecyclerViews
        restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize manager
        manager = new Manager();

        // Fetch current location
        getCurrentLocation();

        // Load initial data
        updateUserContext();

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

        // Request location updates
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 60 * 1000); // Update every 10 minutes

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) return;
            for (Location location : locationResult.getLocations()) {
                currentLocation = location;
                updateLocationText(location);
            }
        }
    };

    private void updateLocationText(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                currentLocationName = address.getLocality() != null ? address.getLocality() : "Unknown Location";
                locationTextView.setText("Current Location: " + currentLocationName);
            } else {
                locationTextView.setText("Current Location: Unknown");
            }
        } catch (IOException e) {
            e.printStackTrace();
            locationTextView.setText("Location: Unavailable");
        }
    }

    private void fetchWeather(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // URL for OpenWeatherMap API
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=metric&appid=" + WEATHER_API_KEY;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> weatherTextView.setText("Weather: Unavailable"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        currentWeather = json.getJSONArray("weather").getJSONObject(0).getString("description");
                        double temperature = json.getJSONObject("main").getDouble("temp");
                        runOnUiThread(() -> weatherTextView.setText("Weather: " + currentWeather + ", " + temperature + "°C"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> weatherTextView.setText("Weather: Unavailable"));
                    }
                } else {
                    runOnUiThread(() -> weatherTextView.setText("Weather: Unavailable"));
                }
            }
        });
    }

    private void updateUserContext() {
        // Simulate user preferences
        String time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":00";
        String breakfast = "8:00AM", lunch = "1:00PM", dinner = "8:00PM";
        String cuisineType = "AMERICAN";
        double avgPrice = 25.0;
        double rating = 4.5;
        int distance = 500;
        String activityPreference = "Outdoor";
        int ambiance = 3;

        // Pass preferences to manager
        manager.Manage(currentWeather, currentLocationName, time, breakfast, lunch, dinner, cuisineType, avgPrice, rating, distance, activityPreference, ambiance);

        // Fetch recommendations
        activityRecommendations = manager.FinalListActivity;
        restaurantRecommendations = manager.FinalListRestaurant;

        // Display recommendations
        displayRecommendations();
    }

    private void displayRecommendations() {
        List<String> activityNames = new ArrayList<>();
        for (Activity activity : activityRecommendations) {
            activityNames.add(activity.name + " - Rating: " + activity.rating);
        }

        List<String> restaurantNames = new ArrayList<>();
        for (Restaurant restaurant : restaurantRecommendations) {
            restaurantNames.add(restaurant.name + " - Ambiance: " + restaurant.ambiance);
        }

        RecommendationAdapter activityAdapter = new RecommendationAdapter(activityNames);
        RecommendationAdapter restaurantAdapter = new RecommendationAdapter(restaurantNames);

        activityRecyclerView.setAdapter(activityAdapter);
        restaurantRecyclerView.setAdapter(restaurantAdapter);
    }

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
