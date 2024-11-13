package com.example.livraison;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.livraison.database.DatabaseHelper;
import com.example.livraison.model.livraison;

public class LivraisonDetailActivity extends AppCompatActivity {

    private TextView textViewAdresseDepart, textViewAdresseDestination, textViewStatut, textViewDateLivraison;
    private int livraisonId;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livraison_detail);

        // Initialize views
        textViewAdresseDepart = findViewById(R.id.textViewAdresseDepart);
        textViewAdresseDestination = findViewById(R.id.textViewAdresseDestination);
        textViewStatut = findViewById(R.id.textViewStatut);
        textViewDateLivraison = findViewById(R.id.textViewDateLivraison);

        // Retrieve the livraison ID passed from the previous activity
        livraisonId = getIntent().getIntExtra("LIVRAISON_ID", -1);

        Log.d("LivraisonDetailActivity", "livraisonId: " + livraisonId);

        if (livraisonId != -1) {
            databaseHelper = new DatabaseHelper(this);
            livraison liv = databaseHelper.obtenirLivraisonParId(livraisonId);

            if (liv != null) {
                // Set the data to the TextViews
                textViewAdresseDepart.setText(liv.getAdresseDepart());
                textViewAdresseDestination.setText(liv.getAdresseDestination());
                textViewStatut.setText(liv.getStatut());
                textViewDateLivraison.setText(liv.getDateLivraison());
            } else {
                // Handle the case where the livraison was not found
                Toast.makeText(this, "Livraison non trouvée", Toast.LENGTH_SHORT).show();
                Log.e("LivraisonDetailActivity", "Livraison not found for ID: " + livraisonId);
            }
        } else {
            // If the livraisonId is invalid, show an error or close the activity
            Log.e("LivraisonDetailActivity", "Invalid or missing livraisonId.");
            finish();  // Close the activity if no valid ID
        }
        if (livraisonId != -1) {
            databaseHelper = new DatabaseHelper(this);
            livraison liv = databaseHelper.obtenirLivraisonParId(livraisonId);

            if (liv != null) {
                // Set the data to the TextViews
                textViewAdresseDepart.setText(liv.getAdresseDepart());
                textViewAdresseDestination.setText(liv.getAdresseDestination());
                textViewStatut.setText(liv.getStatut());
                textViewDateLivraison.setText(liv.getDateLivraison());
            } else {
                // Handle the case where the livraison was not found
                Toast.makeText(this, "Livraison non trouvée", Toast.LENGTH_SHORT).show();
                Log.e("LivraisonDetailActivity", "Livraison not found for ID: " + livraisonId);
            }
        }
    }
}
