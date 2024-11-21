package com.example.travelcompanion;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

public class NotificationService extends Service {

    private static final String CHANNEL_ID = "travel_companion_notifications";
    private static final int NOTIFICATION_ID = 1;
    private final Handler handler = new Handler();
    private int notificationIndex = 0;

    // Sample notification data
    private final List<String> notifications = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize notification channel
        createNotificationChannel();

        // Add sample notifications
        notifications.add("Activity Suggestion: Visit the Botanical Gardens!");
        notifications.add("Restaurant Recommendation: Try the amazing pasta at Italian Bistro!");

        // Start periodic notifications
        handler.post(notificationRunnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private final Runnable notificationRunnable = new Runnable() {
        @Override
        public void run() {
            if (!notifications.isEmpty()) {
                sendNotification(notifications.get(notificationIndex));
                notificationIndex = (notificationIndex + 1) % notifications.size(); // Cycle notifications
            }

            // Repeat every 30 seconds
            handler.postDelayed(this, 30 * 1000);
        }
    };

    private void sendNotification(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Travel Companion")
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Travel Companion Notifications";
            String description = "Notifications for personalized suggestions";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(notificationRunnable); // Stop notifications when service stops
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // Not binding, used for background service only
    }
}
