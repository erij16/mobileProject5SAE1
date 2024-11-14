package com.example.reclamation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reclamation.database.AppDatabase;
import com.example.reclamation.entities.Reclamation;
import com.example.reclamation.entities.StatusReclamation;

import java.util.List;
import java.util.concurrent.Executors;

public class ReclamationClientAdapter extends RecyclerView.Adapter<ReclamationClientAdapter.ReclamationViewHolder> {
    private List<Reclamation> reclamationList;
    private AppDatabase appDatabase;

    public ReclamationClientAdapter(List<Reclamation> reclamationList, AppDatabase appDatabase) {
        this.reclamationList = reclamationList;
        this.appDatabase = appDatabase;
    }

    @NonNull
    @Override
    public ReclamationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reclamationclient, parent, false);
        return new ReclamationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReclamationViewHolder holder, int position) {
        Reclamation reclamation = reclamationList.get(position);
        holder.typeTextView.setText(reclamation.getType().toString());
        holder.descriptionTextView.setText(reclamation.getDescription());
        holder.statusTextView.setText(reclamation.getStatus().toString());

        if (reclamation.getStatus() == StatusReclamation.PENDING) {
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setOnClickListener(v -> removeItem(position, reclamation, holder.itemView));
        } else {
            holder.deleteButton.setVisibility(View.GONE);
        }
    }

    private void removeItem(int position, Reclamation reclamation, View itemView) {
        Executors.newSingleThreadExecutor().execute(() -> {
            appDatabase.reclamationDAO().deleteReclamation(reclamation);
            itemView.post(() -> {
                Toast.makeText(itemView.getContext(), "Reclamation supprimé avec succès", Toast.LENGTH_SHORT).show();
                // Remove the item from the list and notify the adapter on the main thread
                reclamationList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, reclamationList.size());
            });
        });
    }

    @Override
    public int getItemCount() {
        return reclamationList.size();
    }

    static class ReclamationViewHolder extends RecyclerView.ViewHolder {
        TextView typeTextView, descriptionTextView, statusTextView;
        Button deleteButton;

        public ReclamationViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
