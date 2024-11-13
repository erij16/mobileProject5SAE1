package com.example.livraison;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.livraison.ListLivraisonActivity;
import com.example.livraison.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handle window insets (this is for edge-to-edge functionality)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the button from the layout
        Button btnViewLivraison = findViewById(R.id.btnViewLivraison);

        // Set the click listener to navigate to LivraisonActivity
        btnViewLivraison.setOnClickListener(v -> {
            // Start LivraisonActivity
            Intent intent = new Intent(MainActivity.this, ListLivraisonActivity.class);
            startActivity(intent);
        });
    }
}
