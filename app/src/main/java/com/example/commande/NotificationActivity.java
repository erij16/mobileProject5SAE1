package com.example.commande;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.NotificationCompat;

public class NotificationActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "commande_channel"; // ID du canal de notification
    private static final String CHANNEL_NAME = "Commande Notifications"; // Nom du canal
    private static final int PERMISSION_REQUEST_CODE = 1; // Code de la demande de permission
    private static final String TAG = "NotificationActivity";  // Log tag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // Demander la permission pour les notifications sur Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Log.d(TAG, "Android version >= 13, checking notification permission");
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission not granted, requesting permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
            } else {
                Log.d(TAG, "Permission granted, creating notification channel");
                createNotificationChannel();
                displayNotification();
            }
        } else {
            Log.d(TAG, "Android version < 13, no permission needed");
            createNotificationChannel();
            displayNotification();
        }

        // Récupérer les données passées par l'intent
        TextView notificationTitle = findViewById(R.id.notificationTitle);
        TextView notificationMessage = findViewById(R.id.notificationMessage);
        String title = getIntent().getStringExtra("title");
        String message = getIntent().getStringExtra("message");

        // Vérifier si les données sont présentes dans l'intent
        if (title != null && message != null) {
            notificationTitle.setText(title);
            notificationMessage.setText(message);
        } else {
            // Si les données sont manquantes, afficher un toast d'erreur
            Toast.makeText(this, "Erreur: Notification invalide", Toast.LENGTH_SHORT).show();
        }
    }

    // Méthode pour créer un NotificationChannel
    private void createNotificationChannel() {
        Log.d(TAG, "Creating notification channel");

        // Vérifier si la version d'Android est 8.0 (API 26) ou supérieure
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Créer le canal de notification
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, // Identifiant unique du canal
                    CHANNEL_NAME, // Nom du canal
                    NotificationManager.IMPORTANCE_DEFAULT // Niveau de priorité
            );
            channel.setDescription("Notifications liées aux commandes"); // Description du canal

            // Enregistrer le canal avec le système
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
                Log.d(TAG, "Notification channel created");
            } else {
                Log.e(TAG, "Failed to create notification channel");
            }
        }
    }

    // Méthode pour afficher la notification
    private void displayNotification() {
        Log.d(TAG, "Displaying notification");

        String title = getIntent().getStringExtra("title");
        String message = getIntent().getStringExtra("message");

        // Assurez-vous que title et message ne sont pas nuls
        if (title != null && message != null) {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(title) // Titre de la notification
                    .setContentText(message) // Message de la notification
                    .setSmallIcon(R.drawable.ic_notification) // Icône de la notification (assurez-vous de la définir dans res/drawable)
                    .setAutoCancel(true) // La notification sera supprimée quand l'utilisateur la clique
                    .build();

            // Afficher la notification
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.notify(1, notification); // ID unique de la notification
                Log.d(TAG, "Notification displayed");
            } else {
                Log.e(TAG, "Failed to show notification");
            }
        } else {
            Log.e(TAG, "Title or message is null, cannot display notification");
        }
    }

    // Gérer la réponse de la demande de permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission granted, creating notification channel and displaying notification");
                createNotificationChannel();
                displayNotification();
            } else {
                Log.d(TAG, "Permission denied");
                Toast.makeText(this, "Permission pour afficher les notifications refusée.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
