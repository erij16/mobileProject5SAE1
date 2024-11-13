package com.example.produitsprimairesf;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.example.produitsprimairesf.Adapter.MyAdapter;
import com.example.produitsprimairesf.database.AppDatabase;
import com.example.produitsprimairesf.entities.ProductP;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText tf_code, tf_nom, tf_quantite, tf_prix_unitaire, tf_seuil_min;
    Button btn_ajout, btn_affichage, btn_back, btnSelectImage;
    ImageView imageView;
    AppDatabase database;

    List<ProductP> productPList;
    MyAdapter myAdapter;

    private static final int IMAGE_PICKER_REQUEST = 1001;
    Uri selectedImageUri; // Variable pour stocker l'URI de l'image sélectionnée

    private static final int REQUEST_CODE_PERMISSION = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Demander la permission d'accéder au stockage externe (si nécessaire)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, REQUEST_CODE_PERMISSION);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
            }
        }


        // Initialisation des éléments de l'interface
        database = AppDatabase.getAppDatabase(this);
        tf_code = findViewById(R.id.tf_code);
        tf_nom = findViewById(R.id.tf_nom);
        tf_quantite = findViewById(R.id.tf_quantite);
        tf_prix_unitaire = findViewById(R.id.tf_prix_unitaire);
        tf_seuil_min = findViewById(R.id.tf_seuil_min);
        btn_ajout = findViewById(R.id.btn_ajout);
        btn_affichage = findViewById(R.id.btn_affichage);
        btn_back = findViewById(R.id.btn_back);
        btnSelectImage = findViewById(R.id.btn_select_image);
        imageView = findViewById(R.id.imageView);

        productPList = new ArrayList<>(database.productDAO().getAllProducts());
        myAdapter = new MyAdapter(this, productPList);

        // Fonctionnalité du bouton Retour
        btn_back.setOnClickListener(v -> finish());

        // Ajouter un produit avec image
        btn_ajout.setOnClickListener(v -> {
            // Vérification que tous les champs sont remplis
            if (tf_code.getText().toString().isEmpty() || tf_nom.getText().toString().isEmpty() ||
                    tf_quantite.getText().toString().isEmpty() || tf_prix_unitaire.getText().toString().isEmpty() ||
                    tf_seuil_min.getText().toString().isEmpty() || selectedImageUri == null) {
                Toast.makeText(MainActivity.this, "Tous les champs doivent être remplis, y compris l'image", Toast.LENGTH_SHORT).show();
                return;
            }

            // Récupérer les valeurs des champs
            String code = tf_code.getText().toString();
            String nom = tf_nom.getText().toString();
            int quantite = Integer.parseInt(tf_quantite.getText().toString());
            double prixUnitaire = Double.parseDouble(tf_prix_unitaire.getText().toString());
            int seuilMin = Integer.parseInt(tf_seuil_min.getText().toString());

            // Contrôle de saisie : La quantité minimale doit être supérieure à la quantité
            if (seuilMin <= quantite) {
                Toast.makeText(MainActivity.this, "La quantité minimale doit être supérieure à la quantité.", Toast.LENGTH_SHORT).show();
                return;  // Empêche l'ajout si la condition n'est pas respectée
            }

            // Créer l'objet ProductP avec les informations saisies
            ProductP productP = new ProductP(code, nom, quantite, prixUnitaire, seuilMin);
            productP.setImageUri(selectedImageUri.toString());

            // Ajouter le produit à la base de données
            database.productDAO().createProduct(productP);

            Toast.makeText(MainActivity.this, "Produit ajouté avec succès", Toast.LENGTH_SHORT).show();
            loadData(); // Recharger les données après ajout
        });


        // Affichage de la liste des produits
        btn_affichage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AffichageActivity.class);
            startActivity(intent);
        });

        // Bouton pour sélectionner une image depuis le répertoire "Pictures"
        btnSelectImage.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    ouvrirGalerie();
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivityForResult(intent, REQUEST_CODE_PERMISSION);
                }
            } else {
                ouvrirGalerie();
            }
        });
    }

    // Méthode pour ouvrir la galerie d'images
    private void ouvrirGalerie() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICKER_REQUEST);
    }

    // Gérer la sélection d'une image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICKER_REQUEST && resultCode == RESULT_OK) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                imageView.setImageURI(selectedImageUri); // Afficher l'image dans l'ImageView
            } else {
                Toast.makeText(this, "Erreur de sélection de l'image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Recharger les données de la base de données
    private void loadData() {
        productPList.clear();
        productPList.addAll(database.productDAO().getAllProducts());
        myAdapter.notifyDataSetChanged();
    }

    // Gérer les résultats des demandes de permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission nécessaire pour accéder aux images", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
