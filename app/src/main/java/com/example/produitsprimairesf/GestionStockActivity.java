package com.example.produitsprimairesf;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class GestionStockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_stock);

        // Référence des boutons
        Button btnAjouterProduits = findViewById(R.id.btn_ajouter_produits);
        Button btnAfficherStock = findViewById(R.id.btn_afficher_stock);

        // Gestion du clic sur le bouton "Ajouter produits"
        btnAjouterProduits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionStockActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Gestion du clic sur le bouton "Afficher stock"
        btnAfficherStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionStockActivity.this, AffichageActivity.class);
                startActivity(intent);
            }
        });
    }
}

