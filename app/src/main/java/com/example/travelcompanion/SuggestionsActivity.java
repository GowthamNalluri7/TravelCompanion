//package com.example.travelcompanion;
//
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.Comparator;
//import java.util.List;
//
//import project.Activity;
//import project.Manager;
//import project.Restaurant;
//
//public class SuggestionsActivity extends AppCompatActivity {
//
//    private TextView locationTextView, activityTextView, restaurantTextView, weatherConditionsTextView;
//    private Button refreshButton;
//
//    private String fixedLocation = "Griffintown"; // Fixed location
//    private String fixedWeather = "Snowy"; // Fixed weather
//    private Manager manager;
//    private List<Activity> activityRecommendations;
//    private List<Restaurant> restaurantRecommendations;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_suggestions);
//
//        // Initialize views
//        locationTextView = findViewById(R.id.locationTextView);
//        weatherConditionsTextView = findViewById(R.id.weatherConditionsTextView);
//        activityTextView = findViewById(R.id.activityTextView);
//        restaurantTextView = findViewById(R.id.restaurantTextView);
//        refreshButton = findViewById(R.id.refreshButton);
//
//        // Initialize Manager
//        manager = new Manager();
//
//        // Fetch recommendations
//        fetchRecommendations();
//
//        // Refresh button functionality
//        refreshButton.setOnClickListener(v -> fetchRecommendations());
//    }
//
//    private void fetchRecommendations() {
//        // Example user inputs
//        String time = "12:00PM"; // Static time input
//        String breakfast = "8:00AM";
//        String lunch = "1:00PM";
//        String dinner = "7:00PM";
//        String cuisineType = "AMERICAN";
//        double avgPrice = 20.0;
//        double rating = 4.0;
//        int distance = 500; // Static distance in meters
//        String activityPreference = fixedWeather.equalsIgnoreCase("Rainy") ? "Indoor" : "Outdoor"; // Weather-based preference
//        int ambiance = 3;
//
//        // Call Manager to get recommendations
//        manager.Manage(fixedWeather, fixedLocation, time, breakfast, lunch, dinner, cuisineType, avgPrice, rating, distance, activityPreference, ambiance);
//
//        // Filter activities based on weather
//        activityRecommendations = manager.FinalListActivity;
//        restaurantRecommendations = manager.FinalListRestaurant;
//
//        // Update UI with recommendations
//        displayRecommendations();
//    }
//
//    private void displayRecommendations() {
//        // Display fixed location
//        locationTextView.setText("Location: " + fixedLocation);
//        weatherConditionsTextView.setText("Weather Conditions: " + fixedWeather);
//
//        // Display activity recommendations
//        StringBuilder activityDisplay = new StringBuilder("-- Activity Suggestions --\n");
//        if (activityRecommendations != null && !activityRecommendations.isEmpty()) {
//            int activityCount = Math.min(activityRecommendations.size(), 3); // Limit to 3 activities
//            for (int i = 0; i < activityCount; i++) {
//                Activity activity = activityRecommendations.get(i);
//                activityDisplay.append(activity.name)
//                        .append(" - Rating: ").append(activity.rating)
//                        .append(" - Cost: $").append(activity.avgCost)
//                        .append("\n");
//            }
//        } else {
//            activityDisplay.append("No activities available.\n");
//        }
//        activityTextView.setText(activityDisplay.toString());
//
//        // Display restaurant recommendations
//        StringBuilder restaurantDisplay = new StringBuilder("-- Restaurant Suggestions --\n");
//        if (restaurantRecommendations != null && !restaurantRecommendations.isEmpty()) {
//            // Sort restaurants by cost in ascending order
//            restaurantRecommendations.sort(Comparator.comparingDouble(r -> r.averageCost));
//
//            int restaurantCount = Math.min(restaurantRecommendations.size(), 3); // Limit to 3 restaurants
//            for (int i = 0; i < restaurantCount; i++) {
//                Restaurant restaurant = restaurantRecommendations.get(i);
//                restaurantDisplay.append(restaurant.name)
//                        .append(" - Ambiance: ").append(restaurant.ambiance)
//                        .append(" - Rating: ").append(restaurant.rating)
//                        .append(" - Cost: $").append(restaurant.averageCost)
//                        .append("\n");
//            }
//        } else {
//            restaurantDisplay.append("No restaurants available.\n");
//        }
//        restaurantTextView.setText(restaurantDisplay.toString());
//    }


package com.example.travelcompanion;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Comparator;
import java.util.List;

import project.Activity;
import project.Manager;
import project.Restaurant;

public class SuggestionsActivity extends AppCompatActivity {

    private TextView locationTextView, weatherConditionsTextView;
    private RecyclerView activityRecyclerView, restaurantRecyclerView;
    private Button refreshButton;

    private String fixedLocation = "Griffintown"; // Fixed location
    private String fixedWeather = "Snowy"; // Fixed weather
    private Manager manager;
    private List<Activity> activityRecommendations;
    private List<Restaurant> restaurantRecommendations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        // Initialize views
        locationTextView = findViewById(R.id.locationTextView);
        weatherConditionsTextView = findViewById(R.id.weatherConditionsTextView);
        activityRecyclerView = findViewById(R.id.activityRecyclerView);
        restaurantRecyclerView = findViewById(R.id.restaurantRecyclerView);
        refreshButton = findViewById(R.id.refreshButton);

        // Initialize Manager
        manager = new Manager();

        // Set up RecyclerViews
        activityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch recommendations
        fetchRecommendations();

        // Refresh button functionality
        refreshButton.setOnClickListener(v -> fetchRecommendations());
    }

    private void fetchRecommendations() {
        // Example user inputs
        String time = "12:00PM"; // Static time input
        String breakfast = "8:00AM";
        String lunch = "1:00PM";
        String dinner = "7:00PM";
        String cuisineType = "AMERICAN";
        double avgPrice = 20.0;
        double rating = 4.0;
        int distance = 500; // Static distance in meters
        String activityPreference = fixedWeather.equalsIgnoreCase("Rainy") ? "Indoor" : "Outdoor"; // Weather-based preference
        int ambiance = 3;

        // Call Manager to get recommendations
        manager.Manage(fixedWeather, fixedLocation, time, breakfast, lunch, dinner, cuisineType, avgPrice, rating, distance, activityPreference, ambiance);

        // Filter activities based on weather
        activityRecommendations = manager.FinalListActivity;
        restaurantRecommendations = manager.FinalListRestaurant;

        // Update UI with recommendations
        displayRecommendations();
    }

    private void displayRecommendations() {
        // Display fixed location and weather
        locationTextView.setText("Location: " + fixedLocation);
        weatherConditionsTextView.setText("Weather Conditions: " + fixedWeather);

        // Set up Activity Adapter
        ActivityAdapter activityAdapter = new ActivityAdapter(activityRecommendations.subList(0, Math.min(3, activityRecommendations.size())));
        activityRecyclerView.setAdapter(activityAdapter);

        // Sort restaurants by cost and set up Restaurant Adapter
        restaurantRecommendations.sort(Comparator.comparingDouble(r -> r.averageCost));
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(restaurantRecommendations.subList(0, Math.min(3, restaurantRecommendations.size())));
        restaurantRecyclerView.setAdapter(restaurantAdapter);
    }
}

