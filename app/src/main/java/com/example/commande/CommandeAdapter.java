package com.example.commande;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.commande.database.DatabaseHelper;
import com.example.commande.model.commande;
import java.util.List;

public class CommandeAdapter extends RecyclerView.Adapter<CommandeAdapter.CommandeViewHolder> {

    private List<commande> commandes;
    private DatabaseHelper databaseHelper;
    private OnDeleteClickListener onDeleteClickListener;
    private OnEditClickListener onEditClickListener; // New listener for edit action

    // Constructor with both delete and edit listeners
    public CommandeAdapter(List<commande> commandes, DatabaseHelper databaseHelper, OnDeleteClickListener onDeleteClickListener, OnEditClickListener onEditClickListener) {
        this.commandes = commandes;
        this.databaseHelper = databaseHelper;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onEditClickListener = onEditClickListener; // Initialize edit listener
    }

    @NonNull
    @Override
    public CommandeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commande, parent, false);
        return new CommandeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommandeViewHolder holder, int position) {
        commande currentCommande = commandes.get(position);
        holder.textViewProduit.setText("Produit: " + currentCommande.getProduit());
        holder.textViewQuantite.setText("Quantité: " + currentCommande.getQuantite());
        holder.textViewPrix.setText("Prix: " + currentCommande.getPrix());

        // Set delete button click listener
        holder.deleteButton.setOnClickListener(v -> {
            onDeleteClickListener.onDeleteClick(position);
        });

        // Set edit button click listener
        holder.editButton.setOnClickListener(v -> {
            onEditClickListener.onEditClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return commandes.size();
    }

    public static class CommandeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProduit, textViewQuantite, textViewPrix;
        ImageView deleteButton, editButton; // Add editButton here

        public CommandeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProduit = itemView.findViewById(R.id.textViewProduit);
            textViewQuantite = itemView.findViewById(R.id.textViewQuantite);
            textViewPrix = itemView.findViewById(R.id.textViewPrix);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton); // Initialize editButton
        }
    }

    // Interface for delete action
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    // Interface for edit action
    public interface OnEditClickListener {
        void onEditClick(int position);
    }
}
