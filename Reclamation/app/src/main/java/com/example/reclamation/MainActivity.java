package com.example.reclamation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reclamation.database.AppDatabase;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Set to the correct layout file

        // Setting up the database instance
        AppDatabase db = AppDatabase.getAppDatabase(this);

        // Initialize buttons and set up click listeners
        Button adminButton = findViewById(R.id.buttonAdmin);
        Button clientButton = findViewById(R.id.buttonClient);

        adminButton.setOnClickListener(v -> {
            // Navigate to ListReclamationsActivity when admin button is clicked
            Intent intent = new Intent(MainActivity.this, ListReclamationsActivity.class);
            startActivity(intent);
        });

        clientButton.setOnClickListener(v -> {
            // Navigate to AddReclamation when client button is clicked
            Intent intent = new Intent(MainActivity.this, AddReclamation.class);
            startActivity(intent);
        });
    }
}
