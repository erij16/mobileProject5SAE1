package com.example.livraison;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livraison.database.DatabaseHelper;
import com.example.livraison.model.livraison;

import java.util.List;

public class LivraisonAdapter extends RecyclerView.Adapter<LivraisonAdapter.LivraisonViewHolder> {

    private List<livraison> livraisons;
    private DatabaseHelper databaseHelper;
    private Context context;

    // Constructor to pass the list of livraisons and the context
    public LivraisonAdapter(Context context, List<livraison> livraisons) {
        this.context = context;
        this.livraisons = livraisons;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public LivraisonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the item view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livraison, parent, false);
        return new LivraisonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LivraisonViewHolder holder, int position) {
        livraison currentLivraison = livraisons.get(position);

        // Set the data into the TextViews
        holder.textViewAdresse.setText("Adresse: " + currentLivraison.getAdresseDestination());
        holder.textViewDate.setText("Date: " + currentLivraison.getDateLivraison());
        holder.textViewStatus.setText("Status: " + currentLivraison.getStatut());

        // Set onClickListener for item view to open detailed page
        holder.itemView.setOnClickListener(v -> {
            // Same check for valid ID before navigating to details page
            if (currentLivraison.getId() > 0) {
                Intent intent = new Intent(context, LivraisonDetailActivity.class);  // Update this activity to show details
                intent.putExtra("livraison_id", currentLivraison.getId());
                context.startActivity(intent);
            } else {
                Log.e("LivraisonAdapter", "Invalid ID for livraison: " + currentLivraison.getId());
            }
        });

        // Set onClickListener for Update button
        holder.buttonUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateLivraisonActivity.class);
            intent.putExtra("livraison_id", currentLivraison.getId());
            context.startActivity(intent);
        });

        // Set onClickListener for Delete button
        holder.buttonDelete.setOnClickListener(v -> {
            databaseHelper.supprimerLivraison(currentLivraison.getId());
            livraisons.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, livraisons.size());
        });
    }

    @Override
    public int getItemCount() {
        return livraisons.size();
    }

    public static class LivraisonViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAdresse, textViewDate, textViewStatus;
        Button buttonUpdate, buttonDelete;

        public LivraisonViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAdresse = itemView.findViewById(R.id.textViewAdresse);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
