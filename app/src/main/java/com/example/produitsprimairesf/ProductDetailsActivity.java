package com.example.produitsprimairesf;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.produitsprimairesf.entities.ProductP;

public class ProductDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Récupérer le produit à partir de l'intent
        ProductP product = getIntent().getParcelableExtra("product");

        // Afficher les informations du produit
        if (product != null) {
            TextView tvName = findViewById(R.id.tv_name);
            TextView tvCode = findViewById(R.id.tv_code);
            TextView tvQuantity = findViewById(R.id.tv_quantity);
            TextView tvPrice = findViewById(R.id.tv_price);
            TextView tvThreshold = findViewById(R.id.tv_threshold);

            tvName.setText(product.getNom());
            tvCode.setText(product.getCode());
            tvQuantity.setText(String.valueOf(product.getQuantite()));
            tvPrice.setText(String.valueOf(product.getPrix_unitaire()));
            tvThreshold.setText(String.valueOf(product.getSeuil_min()));
        }
    }
}
