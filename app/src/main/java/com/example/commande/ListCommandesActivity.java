package com.example.commande;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.commande.database.DatabaseHelper;
import com.example.commande.model.commande;
import java.util.List;

public class ListCommandesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommandeAdapter commandeAdapter;
    private DatabaseHelper databaseHelper;
    private List<commande> commandes;
    private TextView notificationMessage; // TextView pour afficher le message de notification

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_commandes);

        // Initialiser la base de données et recyclerView
        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewCommandes);

        // Initialiser la liste des commandes
        commandes = databaseHelper.obtenirToutesLesCommandes();

        // Vérifier si des commandes existent
        if (commandes.isEmpty()) {
            Toast.makeText(this, "Aucune commande trouvée", Toast.LENGTH_SHORT).show();
        }

        // Initialiser l'adaptateur et lier au RecyclerView avec les écouteurs de suppression et d'édition
        commandeAdapter = new CommandeAdapter(commandes, databaseHelper,
                new CommandeAdapter.OnDeleteClickListener() {
                    @Override
                    public void onDeleteClick(int position) {
                        showDeleteConfirmationDialog(position);  // Afficher une boîte de confirmation avant la suppression
                    }
                },
                new CommandeAdapter.OnEditClickListener() {
                    @Override
                    public void onEditClick(int position) {
                        editCommande(position);  // Modifier la commande
                    }
                }
        );
        recyclerView.setAdapter(commandeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configurer le bouton Ajouter Commande
        Button btnAjouterCommande = findViewById(R.id.btnAjouterCommande);
        btnAjouterCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListCommandesActivity.this, AddCommandeActivity.class);
                startActivity(intent);
            }
        });

        // Initialiser le TextView pour afficher le message de notification
        notificationMessage = findViewById(R.id.notificationMessage);

        // Récupérer le message passé par l'Intent
        String message = getIntent().getStringExtra("message");

        // Afficher le message si présent
        if (message != null) {
            notificationMessage.setText(message);
            notificationMessage.setVisibility(View.VISIBLE); // Afficher la notification

            // Appliquer une animation pour la faire apparaître
            notificationMessage.setAlpha(0f); // Début de l'animation (invisible)
            notificationMessage.animate().alpha(1f).setDuration(500); // Apparition progressive

            // Masquer le message après 3 secondes
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    notificationMessage.animate().alpha(0f).setDuration(500); // Disparition progressive
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            notificationMessage.setVisibility(View.GONE); // Cacher après animation
                        }
                    }, 500);
                }
            }, 3000); // 3 secondes
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Rafraîchir la liste des commandes après retour de l'activité AddCommandeActivity ou UpdateDeleteCommandeActivity
        commandes.clear();
        commandes.addAll(databaseHelper.obtenirToutesLesCommandes());
        commandeAdapter.notifyDataSetChanged();
    }

    // Méthode pour supprimer la commande avec une confirmation
    private void showDeleteConfirmationDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation de suppression")
                .setMessage("Êtes-vous sûr de vouloir supprimer cette commande ?")
                .setPositiveButton("Oui", (dialog, which) -> deleteCommande(position)) // Si l'utilisateur confirme
                .setNegativeButton("Non", null) // Si l'utilisateur annule
                .show();
    }

    private void deleteCommande(int position) {
        commande cmd = commandes.get(position);
        int rowsDeleted = databaseHelper.supprimerCommande(cmd.getId());  // Utiliser int au lieu de boolean

        if (rowsDeleted > 0) {
            // Notification de suppression
            showNotification("Commande supprimée avec succès", R.color.colorRed);
            commandes.remove(position);
            commandeAdapter.notifyItemRemoved(position);
        } else {
            Toast.makeText(ListCommandesActivity.this, "Erreur lors de la suppression de la commande", Toast.LENGTH_SHORT).show();
        }
    }

    // Méthode pour modifier une commande
    private void editCommande(int position) {
        commande cmd = commandes.get(position);
        Intent intent = new Intent(ListCommandesActivity.this, UpdateDeleteCommandeActivity.class);
        intent.putExtra("commandeId", cmd.getId());
        intent.putExtra("produit", cmd.getProduit());
        intent.putExtra("quantite", cmd.getQuantite());
        intent.putExtra("prix", cmd.getPrix());
        startActivity(intent);

        // Notification de modification - Utilisation de la couleur orange
        showNotification("Commande modifiée avec succès", R.color.colorTransparent);  // Notification orange pour la modification
    }

    private void showNotification(String message, int colorRes) {
        // Mettre à jour le message de notification
        notificationMessage.setText(message);
        notificationMessage.setBackgroundColor(getResources().getColor(colorRes));

        // Afficher la notification
        notificationMessage.setVisibility(View.VISIBLE);
        notificationMessage.setAlpha(0f); // Commence invisible
        notificationMessage.animate().alpha(1f).setDuration(500); // Apparition progressive

        // Cacher la notification après 4 secondes
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notificationMessage.animate().alpha(0f).setDuration(500); // Disparition progressive
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notificationMessage.setVisibility(View.GONE); // Cacher après animation
                    }
                }, 500);
            }
        }, 4000); // 4 secondes
    }
}
