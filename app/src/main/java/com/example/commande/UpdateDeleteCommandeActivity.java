package com.example.commande;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.commande.database.DatabaseHelper;
import com.example.commande.model.commande;

public class UpdateDeleteCommandeActivity extends AppCompatActivity {

    private EditText editTextProduit, editTextQuantite, editTextPrix;
    private Button buttonUpdateCommande, buttonDeleteCommande;
    private int commandeId;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_commande);

        // Initialisation des éléments de l'interface utilisateur
        editTextProduit = findViewById(R.id.editTextProduit);
        editTextQuantite = findViewById(R.id.editTextQuantite);
        editTextPrix = findViewById(R.id.editTextPrix);
        buttonUpdateCommande = findViewById(R.id.buttonUpdateCommande);
        buttonDeleteCommande = findViewById(R.id.buttonDeleteCommande);

        // Initialisation de la base de données
        databaseHelper = new DatabaseHelper(this);

        // Récupérer l'ID de la commande à modifier depuis l'intent
        commandeId = getIntent().getIntExtra("commandeId", -1);
        String produit = getIntent().getStringExtra("produit");
        int quantite = getIntent().getIntExtra("quantite", 0);
        double prix = getIntent().getDoubleExtra("prix", 0.0);

        // Afficher les valeurs de la commande dans les champs
        editTextProduit.setText(produit);
        editTextQuantite.setText(String.valueOf(quantite));
        editTextPrix.setText(String.valueOf(prix));

        // Mettre à jour la commande
        buttonUpdateCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String produit = editTextProduit.getText().toString();
                String quantiteText = editTextQuantite.getText().toString();
                String prixText = editTextPrix.getText().toString();

                // Vérifier si les champs sont vides
                if (produit.isEmpty() || quantiteText.isEmpty() || prixText.isEmpty()) {
                    Toast.makeText(UpdateDeleteCommandeActivity.this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int quantite = Integer.parseInt(quantiteText);
                    double prix = Double.parseDouble(prixText);

                    // Mettre à jour la commande dans la base de données
                    commande updatedCommande = new commande(commandeId, produit, quantite, prix);
                    int rowsUpdated = databaseHelper.mettreAJourCommande(updatedCommande);

                    if (rowsUpdated > 0) {
                        Toast.makeText(UpdateDeleteCommandeActivity.this, "Commande mise à jour avec succès", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UpdateDeleteCommandeActivity.this, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(UpdateDeleteCommandeActivity.this, "Veuillez entrer des valeurs valides pour la quantité et le prix", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Supprimer la commande
        buttonDeleteCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rowsDeleted = databaseHelper.supprimerCommande(commandeId);

                if (rowsDeleted > 0) {
                    Toast.makeText(UpdateDeleteCommandeActivity.this, "Commande supprimée avec succès", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateDeleteCommandeActivity.this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
