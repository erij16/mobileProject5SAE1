package com.example.livraison;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.livraison.database.DatabaseHelper;
import com.example.livraison.model.livraison;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddLivraisonActivity extends AppCompatActivity {

    private EditText editTextAdresseDepart, editTextAdresseDestination, editTextStatut, editTextDateLivraison;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_livraison);

        // Initialize UI elements
        editTextAdresseDepart = findViewById(R.id.editTextAdresseDepart);
        editTextAdresseDestination = findViewById(R.id.editTextAdresseDestination);
        editTextStatut = findViewById(R.id.editTextStatut);
        editTextDateLivraison = findViewById(R.id.editTextDateLivraison);

        Button buttonAddLivraison = findViewById(R.id.buttonAddLivraison);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Set action for the Add button
        buttonAddLivraison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve values from text fields
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

                // Validate the date format
                if (!isValidDateFormat(dateLivraison)) {
                    editTextDateLivraison.setError("Invalid date format (dd/MM/yyyy expected)");
                    return;
                }

                // Create a new livraison object with validated data
                livraison livraison = new livraison(adresseDepart, adresseDestination, statut, dateLivraison);

                // Add the livraison to the database
                long result = databaseHelper.ajouterLivraison(livraison);

                if (result > 0) {
                    Toast.makeText(AddLivraisonActivity.this, "Livraison ajoutée avec succès", Toast.LENGTH_SHORT).show();

                    // Redirect to the main activity
                    Intent intent = new Intent(AddLivraisonActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddLivraisonActivity.this, "Erreur lors de l'ajout de la livraison", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to validate date format (example: "dd/MM/yyyy")
    private boolean isValidDateFormat(String date) {
        String datePattern = "^(0[1-9]|1[0-9]|2[0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$"; // Regex for dd/MM/yyyy
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }
}
