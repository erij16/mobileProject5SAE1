package com.example.commande;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotificationUtils {

    // Unique identifier for the notification channel
    private static final String CHANNEL_ID = "commande_channel";
    // Name and description for the notification channel
    private static final String CHANNEL_NAME = "Commande Notifications";
    private static final String CHANNEL_DESCRIPTION = "Notifications liées aux commandes";

    // Method to create a notification channel
    public static void createNotificationChannel(Context context) {
        // Check if the Android version is at least 8.0 (API 26)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,  // The unique ID for the channel
                    CHANNEL_NAME,  // The name of the channel
                    NotificationManager.IMPORTANCE_DEFAULT  // Set the importance level of the channel
            );
            channel.setDescription(CHANNEL_DESCRIPTION);  // Set a description for the channel

            // Get the NotificationManager system service
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                // Register the channel with the system
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
