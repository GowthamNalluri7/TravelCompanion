package com.example.travelcompanion;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuggestionsActivity extends AppCompatActivity {

    private TextView locationTextView, weatherTextView, activitySuggestions, restaurantSuggestions;
    private Button refreshButton;

    private List<String> locationList = new ArrayList<>();
    private List<List<String>> activitySuggestionList = new ArrayList<>();
    private List<List<String>> restaurantSuggestionList = new ArrayList<>();
    private Handler notificationHandler;
    private Runnable notificationRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        // Initialize views
        locationTextView = findViewById(R.id.locationTextView);
        weatherTextView = findViewById(R.id.weatherTextView);
        activitySuggestions = findViewById(R.id.activitySuggestions);
        restaurantSuggestions = findViewById(R.id.restaurantSuggestions);
        refreshButton = findViewById(R.id.refreshButton);

        // Populate sample data
        populateData();

        // Display random location and suggestions
        displaySuggestions();

        // Refresh button listener
        refreshButton.setOnClickListener(v -> displaySuggestions());

        // Setup notifications
        setupNotifications();
    }

    private void populateData() {
        // Sample locations with lat/long
        locationList.add("Berri UQAM, 45.5151, -73.5667");
        locationList.add("Old Port, 45.5024, -73.5541");
        locationList.add("Cote Vertu, 45.5161, -73.6984");
        locationList.add("Snowdon, 45.4934, -73.6293");

        // Sample activity suggestions
        for (int i = 0; i < 2; i++) {
            List<String> activitySet = new ArrayList<>();
            activitySet.add("Chess Club");
            activitySet.add("Hiking Trail");
            activitySet.add("Yoga Studio");
            activitySet.add("Bowling Alley");
            activitySet.add("Art Workshop");
            activitySuggestionList.add(activitySet);
        }

        // Sample restaurant suggestions
        for (int i = 0; i < 2; i++) {
            List<String> restaurantSet = new ArrayList<>();
            restaurantSet.add("Sunrise Cafe");
            restaurantSet.add("Steakhouse");
            restaurantSet.add("Pasta Delight");
            restaurantSet.add("Sushi Hub");
            restaurantSet.add("Burger Corner");
            restaurantSet.add("Vegetarian Paradise");
            restaurantSuggestionList.add(restaurantSet);
        }
    }

    private void displaySuggestions() {
        // Pick random location
        Random random = new Random();
        String randomLocation = locationList.get(random.nextInt(locationList.size()));
        String[] locationParts = randomLocation.split(", ");
        locationTextView.setText("Location: " + locationParts[0]);

        // Mock weather display
        weatherTextView.setText("Live Temperature: " + random.nextInt(30) + "°C");

        // Pick random suggestions
        List<String> randomActivitySuggestions = activitySuggestionList.get(random.nextInt(activitySuggestionList.size()));
        List<String> randomRestaurantSuggestions = restaurantSuggestionList.get(random.nextInt(restaurantSuggestionList.size()));

        // Display suggestions
        activitySuggestions.setText("-- Activity Suggestions --\n" + String.join("\n", randomActivitySuggestions));
        restaurantSuggestions.setText("-- Restaurant Suggestions --\n" + String.join("\n", randomRestaurantSuggestions));
    }

    private void setupNotifications() {
        notificationHandler = new Handler();
        notificationRunnable = new Runnable() {
            @Override
            public void run() {
                sendNotification();
                notificationHandler.postDelayed(this, 30000); // 30 seconds
            }
        };
        notificationHandler.post(notificationRunnable);
    }

    private void sendNotification() {
        // Build notification content
        String content = "Location: " + locationTextView.getText().toString() +
                "\nWeather: " + weatherTextView.getText().toString() +
                "\nSuggestions: " + activitySuggestions.getText().toString();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "TravelCompanionNotifications";

        // Create notification channel (for Android O+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Travel Companion", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Travel Companion Suggestions")
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(1, builder.build());
    }

    private void startNotificationService() {
        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
    }


}
