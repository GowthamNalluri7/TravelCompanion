//package com.example.travelcompanion;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.NotificationCompat;
//
//import com.google.android.gms.location.LocationServices;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;

package com.example.travelcompanion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SuggestionsActivity extends AppCompatActivity {

    private TextView locationTextView, temperatureTextView, activityTextView, restaurantTextView, weatherConditionsTextView;
    private Button refreshButton;

    // Data structures for recommendations
    private Map<String, List<String>> activityMapping;
    private Map<String, List<String>> restaurantMapping;

    private String fixedLocation = "Old Port"; // Fixed location for this session
    private int activityIndex = 0; // Keeps track of activity recommendations
    private int restaurantIndex = 0; // Keeps track of restaurant recommendations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        // Initialize views
        locationTextView = findViewById(R.id.locationTextView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        weatherConditionsTextView = findViewById(R.id.weatherConditionsTextView);
        activityTextView = findViewById(R.id.activityTextView);
        restaurantTextView = findViewById(R.id.restaurantTextView);
        refreshButton = findViewById(R.id.refreshButton);


        // Populate static data
        PopulateData();

        // Display initial recommendations
        updateUI();

        // Refresh button functionality
        refreshButton.setOnClickListener(v -> refreshRecommendations());
    }

    private void PopulateData() {
        // Static mapping of activities and restaurants for different locations
        activityMapping = new HashMap<>();
        restaurantMapping = new HashMap<>();

        // Berri UQAM Activities
        List<String> berriActivities = new ArrayList<>();
        berriActivities.add("Visit Berri Park");
        berriActivities.add("Explore the Underground City");
        berriActivities.add("Enjoy a concert at Place des Arts");
        activityMapping.put("Berri UQAM", berriActivities);

        // Berri UQAM Restaurants
        List<String> berriRestaurants = new ArrayList<>();
        berriRestaurants.add("La Banquise - Famous for Poutine");
        berriRestaurants.add("St-Hubert - Classic Canadian Cuisine");
        berriRestaurants.add("Bouillon Bilk - Modern Fine Dining");
        restaurantMapping.put("Berri UQAM", berriRestaurants);

        // Old Port Activities
        List<String> oldPortActivities = new ArrayList<>();
        oldPortActivities.add("Take a stroll along the waterfront");
        oldPortActivities.add("Explore the Montreal Science Centre");
        oldPortActivities.add("Ride the Ferris Wheel");
        activityMapping.put("Old Port", oldPortActivities);

        // Old Port Restaurants
        List<String> oldPortRestaurants = new ArrayList<>();
        oldPortRestaurants.add("Garde Manger - Seafood Delights");
        oldPortRestaurants.add("Olive et Gourmando - Artisanal Sandwiches");
        oldPortRestaurants.add("Les 400 Coups - French Bistro");
        restaurantMapping.put("Old Port", oldPortRestaurants);
    }

    private void updateUI() {
        locationTextView.setText("Location: " + fixedLocation);

        // Fetch real-time temperature for the fixed location
        if (fixedLocation.equals("Berri UQAM")) {
            fetchTemperature(45.5152, -73.5624); // Latitude and Longitude for Berri UQAM
        } else if (fixedLocation.equals("Old Port")) {
            fetchTemperature(45.5024, -73.5546); // Latitude and Longitude for Old Port
        }

        displayRecommendations();
    }

    private void fetchTemperature(double latitude, double longitude) {
        String apiKey = "ef10fcced2ae0f630a729efe0bf52d2c"; // Replace with your OpenWeather API key
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=metric&appid=" + apiKey;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> temperatureTextView.setText("Live Temperature: Unavailable"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseData);
                        double temperature = json.getJSONObject("main").getDouble("temp");
                        String weatherCondition = "Sunny"; // Static value
                        // String weatherCondition = "Cloudy";
                        // String weatherCondition = "Rainy";
                        // String weatherCondition = "Snowy";

                        runOnUiThread(() -> {
                            temperatureTextView.setText("Live Temperature: " + temperature + "°C");
                            weatherConditionsTextView.setText("Weather Conditions: " + weatherCondition);
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> {
                            Toast.makeText(SuggestionsActivity.this, "Error parsing temperature data", Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(SuggestionsActivity.this, "Failed to fetch temperature", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }



    private void displayRecommendations() {
        // Get the current lists for activities and restaurants
        List<String> activities = activityMapping.get(fixedLocation);
        List<String> restaurants = restaurantMapping.get(fixedLocation);

        // Display current recommendations (2 at a time)
        StringBuilder activityDisplay = new StringBuilder("-- Activity Suggestions --\n");
        StringBuilder restaurantDisplay = new StringBuilder("-- Restaurant Suggestions --\n");

        for (int i = 0; i < 2; i++) {
            int activityPos = (activityIndex + i) % activities.size();
            int restaurantPos = (restaurantIndex + i) % restaurants.size();

            activityDisplay.append(activities.get(activityPos)).append("\n");
            restaurantDisplay.append(restaurants.get(restaurantPos)).append("\n");
        }

        activityTextView.setText(activityDisplay.toString());
        restaurantTextView.setText(restaurantDisplay.toString());
    }

    private void refreshRecommendations() {
        // Increment indexes for cycling recommendations
        activityIndex = (activityIndex + 2) % activityMapping.get(fixedLocation).size();
        restaurantIndex = (restaurantIndex + 2) % restaurantMapping.get(fixedLocation).size();

        // Refresh the displayed recommendations
        displayRecommendations();
    }
}

