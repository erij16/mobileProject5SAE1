package com.example.commande;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.commande.database.DatabaseHelper;
import com.example.commande.model.commande;

public class AddCommandeActivity extends AppCompatActivity {

    private EditText editTextProduit, editTextQuantite, editTextPrix;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_commande);

        // Initialiser les éléments de l'interface utilisateur
        editTextProduit = findViewById(R.id.editTextProduit);
        editTextQuantite = findViewById(R.id.editTextQuantite);
        editTextPrix = findViewById(R.id.editTextPrix);
        Button buttonAddCommande = findViewById(R.id.buttonAddCommande);

        // Initialiser le helper pour la base de données
        databaseHelper = new DatabaseHelper(this);

        // Définir l'action du bouton Ajouter
        buttonAddCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les valeurs des champs de texte
                String produit = editTextProduit.getText().toString();
                String quantiteText = editTextQuantite.getText().toString();
                String prixText = editTextPrix.getText().toString();

                // Vérifier si les champs sont vides
                if (produit.isEmpty() || quantiteText.isEmpty() || prixText.isEmpty()) {
                    Toast.makeText(AddCommandeActivity.this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convertir la quantité et le prix en leurs types appropriés
                int quantite = Integer.parseInt(quantiteText);
                double prix = Double.parseDouble(prixText);

                // Créer un objet Commande
                commande commande = new commande(0, produit, quantite, prix);

                // Ajouter la commande dans la base de données locale SQLite
                long result = databaseHelper.ajouterCommande(commande);

                if (result != -1) {
                    // Envoi de la commande à l'API Node.js
                    envoyerCommandeAuServeur(commande);

                    // Afficher un message dans la liste des commandes
                    Intent listIntent = new Intent(AddCommandeActivity.this, ListCommandesActivity.class);
                    listIntent.putExtra("message", "Commande ajoutée avec succès !");
                    startActivity(listIntent);
                    finish();  // Fermer l'activité actuelle
                } else {
                    Toast.makeText(AddCommandeActivity.this, "Erreur lors de l'ajout de la commande", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void envoyerCommandeAuServeur(commande commande) {
        // Logique pour envoyer la commande au serveur via Retrofit
    }
}
