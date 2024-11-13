package com.example.commande;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
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
                        deleteCommande(position);  // Supprimer la commande
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Rafraîchir la liste des commandes après retour de l'activité AddCommandeActivity
        commandes.clear();
        commandes.addAll(databaseHelper.obtenirToutesLesCommandes());
        commandeAdapter.notifyDataSetChanged();
    }

    private void deleteCommande(int position) {
        commande cmd = commandes.get(position);
        int rowsDeleted = databaseHelper.supprimerCommande(cmd.getId());  // Utiliser int au lieu de boolean

        if (rowsDeleted > 0) {
            Toast.makeText(ListCommandesActivity.this, "Commande supprimée avec succès", Toast.LENGTH_SHORT).show();
            commandes.remove(position);
            commandeAdapter.notifyItemRemoved(position);
        } else {
            Toast.makeText(ListCommandesActivity.this, "Erreur lors de la suppression de la commande", Toast.LENGTH_SHORT).show();
        }
    }

    private void editCommande(int position) {
        commande cmd = commandes.get(position);
        Intent intent = new Intent(ListCommandesActivity.this, UpdateDeleteCommandeActivity.class);
        intent.putExtra("commandeId", cmd.getId());
        intent.putExtra("produit", cmd.getProduit());
        intent.putExtra("quantite", cmd.getQuantite());
        intent.putExtra("prix", cmd.getPrix());
        startActivity(intent);
    }
}
