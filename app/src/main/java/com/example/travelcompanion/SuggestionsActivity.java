package com.example.travelcompanion;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import project.Activity;
import project.Manager;
import project.Restaurant;

public class SuggestionsActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "recommendations_channel";

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a"); // Format as HH:MM AM/PM
        return sdf.format(Calendar.getInstance().getTime());
    }

    private TextView locationTextView, weatherConditionsTextView;
    private RecyclerView activityRecyclerView, restaurantRecyclerView;
    private Button refreshButton;

    private String fixedLocation = "Old Montreal"; // Fixed location
    private String fixedWeather = "Sunny"; // Fixed weather
    private String fixedTime = getCurrentTime();

    private Manager manager;
    private List<Activity> activityRecommendations;
    private List<Restaurant> restaurantRecommendations;
    private int currentActivityIndex = 0;
    private int currentRestaurantIndex = 0;

    Logger logger = Logger.getLogger(SuggestionsActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        // Request Notification Permission for Android 13+
        requestNotificationPermission();

        createNotificationChannel();

        // Find the TextView by its ID
        TextView timeTextView = findViewById(R.id.timeTextView);

        // Get current time and set it
        timeTextView.setText(getCurrentTime());

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
        refreshButton.setOnClickListener(v -> refreshSuggestions());
    }

    private void requestNotificationPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Recommendations Channel";
            String description = "Channel for recommendation notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void refreshActivities() {
        if (activityRecommendations == null || activityRecommendations.isEmpty()) {
            // If there are no activities, don't refresh and log the case
            logger.info("No activity recommendations available to refresh.");
            return;
        }

        int activitySize = activityRecommendations.size();

        // Get the next 2 activities
        List<Activity> nextActivities = new ArrayList<>();
        for (int i = 0; i < 2 && i < activitySize; i++) {
            nextActivities.add(activityRecommendations.get((currentActivityIndex + i) % activitySize));
        }

        // Update the RecyclerView with the new activities
        ActivityAdapter activityAdapter = new ActivityAdapter(nextActivities);
        activityRecyclerView.setAdapter(activityAdapter);

        // Update the current activity index
        currentActivityIndex = (currentActivityIndex + 2) % activitySize;

        // Notify the user about the refresh
        sendActivityNotification(nextActivities);
    }

    private void refreshRestaurants() {
        if (restaurantRecommendations == null || restaurantRecommendations.isEmpty()) {
            // If there are no restaurants, don't refresh and log the case
            logger.info("No restaurant recommendations available to refresh.");
            return;
        }

        int restaurantSize = restaurantRecommendations.size();

        // Get the next restaurant
        List<Restaurant> nextRestaurant = new ArrayList<>();
        nextRestaurant.add(restaurantRecommendations.get(currentRestaurantIndex % restaurantSize));

        // Update the RecyclerView with the new restaurant
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(nextRestaurant);
        restaurantRecyclerView.setAdapter(restaurantAdapter);

        // Update the current restaurant index
        currentRestaurantIndex = (currentRestaurantIndex + 1) % restaurantSize;

        // Notify the user about the refresh
        sendRestaurantNotification(nextRestaurant);
    }

    private void refreshSuggestions() {
        refreshActivities();    // Refresh activity recommendations
        refreshRestaurants();  // Refresh restaurant recommendations
    }

    private void fetchRecommendations() {
        // Example user inputs
        Intent intent = getIntent();
        String breakfast = intent.getStringExtra("breakfastTime");
        String lunch = intent.getStringExtra("lunchTime");
        String dinner = intent.getStringExtra("dinnerTime");
        String cuisineType = intent.getStringExtra("preferredCuisine");
        double avgPrice = intent.getDoubleExtra("avgCost", 0.0);
        double rating = intent.getDoubleExtra("preferredRating", 0.0);
        int distance = intent.getIntExtra("maxDistance", 0); // Dynamic distance in meters
        String activityPreference = fixedWeather.equalsIgnoreCase("Rainy") ? "Indoor" : "Outdoor"; // Weather-based preference
        int ambiance = intent.getIntExtra("preferredAmbience", 0);

        // Call Manager to get recommendations
        manager.Manage(fixedWeather, fixedLocation, fixedTime, breakfast, lunch, dinner, cuisineType, avgPrice, rating, distance, activityPreference, ambiance);

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
        ActivityAdapter activityAdapter = new ActivityAdapter(activityRecommendations.subList(0, Math.min(2, activityRecommendations.size())));
        activityRecyclerView.setAdapter(activityAdapter);

        // Sort restaurants by cost and set up Restaurant Adapter
        restaurantRecommendations.sort(Comparator.comparingDouble(r -> r.averageCost));

        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(restaurantRecommendations.subList(0, Math.min(1, restaurantRecommendations.size())));
        restaurantRecyclerView.setAdapter(restaurantAdapter);
    }

    private void sendActivityNotification(List<Activity> activities) {
        StringBuilder content = new StringBuilder("Activities Updated:\n");
        for (Activity activity : activities) {
            content.append(activity.name).append(" - Rating: ").append(activity.rating).append("\n");
        }
        sendNotification("Activity Recommendations", content.toString());
    }

    private void sendRestaurantNotification(List<Restaurant> restaurants) {
        StringBuilder content = new StringBuilder("Restaurants Updated:\n");
        for (Restaurant restaurant : restaurants) {
            content.append(restaurant.name).append(" - Cost: ").append(restaurant.averageCost).append("\n");
        }
        sendNotification("Restaurant Recommendations", content.toString());
    }

    private void sendNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            logger.info("Notification permission not granted.");
            return;
        }
        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
