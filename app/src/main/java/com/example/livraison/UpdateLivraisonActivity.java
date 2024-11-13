package com.example.livraison;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.livraison.database.DatabaseHelper;
import com.example.livraison.model.livraison;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateLivraisonActivity extends AppCompatActivity {

    private EditText editTextAdresseDepart, editTextAdresseDestination, editTextStatut, editTextDateLivraison;
    private Button buttonSave;
    private DatabaseHelper databaseHelper;
    private int livraisonId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_livraison);

        // Initialize views
        editTextAdresseDepart = findViewById(R.id.editTextAdresseDepart);
        editTextAdresseDestination = findViewById(R.id.editTextAdresseDestination);
        editTextStatut = findViewById(R.id.editTextStatut);
        editTextDateLivraison = findViewById(R.id.editTextDateLivraison);
        buttonSave = findViewById(R.id.buttonSave);

        databaseHelper = new DatabaseHelper(this);

        // Get the livraison ID from the intent
        livraisonId = getIntent().getIntExtra("livraison_id", -1);
        if (livraisonId != -1) {
            // Load the existing livraison details from the database
            loadLivraisonDetails(livraisonId);
        }

        // Set up the save button to update the livraison in the database
        buttonSave.setOnClickListener(v -> updateLivraison());
    }

    private void loadLivraisonDetails(int id) {
        // Fetch the livraison details using the id
        livraison liv = databaseHelper.obtenirLivraisonParId(id);
        if (liv != null) {
            // Populate the fields with existing data
            editTextAdresseDepart.setText(liv.getAdresseDepart());
            editTextAdresseDestination.setText(liv.getAdresseDestination());
            editTextStatut.setText(liv.getStatut());
            editTextDateLivraison.setText(liv.getDateLivraison());
        }
    }

    private void updateLivraison() {
        // Get updated values from the fields
        String adresseDepart = editTextAdresseDepart.getText().toString().trim();
        String adresseDestination = editTextAdresseDestination.getText().toString().trim();
        String statut = editTextStatut.getText().toString().trim();
        String dateLivraison = editTextDateLivraison.getText().toString().trim();

        // Perform validation checks
        if (adresseDepart.isEmpty()) {
            editTextAdresseDepart.setError("Adresse départ is required");
            return;
        }

        if (adresseDestination.isEmpty()) {
            editTextAdresseDestination.setError("Adresse destination is required");
            return;
        }

        if (statut.isEmpty()) {
            editTextStatut.setError("Statut is required");
            return;
        }

        // Validate the date format (optional: depending on your format)
        if (!isValidDateFormat(dateLivraison)) {
            editTextDateLivraison.setError("Invalid date format");
            return;
        }

        // Create a new livraison object with updated values
        livraison updatedLivraison = new livraison(livraisonId, adresseDepart, adresseDestination, statut, dateLivraison);

        // Update the livraison in the database
        int result = databaseHelper.mettreAJourLivraison(updatedLivraison);

        if (result > 0) {
            Toast.makeText(this, "Livraison updated successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity and return to the previous screen
        } else {
            Toast.makeText(this, "Failed to update livraison", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to validate date format (example: "dd/MM/yyyy")
    private boolean isValidDateFormat(String date) {
        String datePattern = "^(0[1-9]|1[0-9]|2[0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$"; // Regex for dd/MM/yyyy
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }
}
