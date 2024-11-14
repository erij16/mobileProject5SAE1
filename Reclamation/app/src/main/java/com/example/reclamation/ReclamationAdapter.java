package com.example.reclamation;

import android.content.Context;
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

public class ReclamationAdapter extends RecyclerView.Adapter<ReclamationAdapter.ReclamationViewHolder> {

    private List<Reclamation> reclamationList;
    private AppDatabase appDatabase;

    // Twilio credentials and contact numbers
    private static final String ACCOUNT_SID = "ACfdba742da56eb7066ad5318c3de7aa0b";
    private static final String AUTH_TOKEN = "2eb147a659ef976dd4d3642ceb7e4975";
    private static final String FROM_NUMBER = "+15084166541";
    private static final String TO_NUMBER = "+21655586868";

    public ReclamationAdapter(List<Reclamation> reclamationList, AppDatabase database) {
        this.reclamationList = reclamationList;
        this.appDatabase = database;
    }

    @NonNull
    @Override
    public ReclamationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reclamation, parent, false);
        return new ReclamationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReclamationViewHolder holder, int position) {
        Reclamation reclamation = reclamationList.get(position);
        holder.typeTextView.setText(reclamation.getType().toString());
        holder.descriptionTextView.setText(reclamation.getDescription());
        holder.statusTextView.setText(reclamation.getStatus().toString());

        // Update button visibility based on the status of the reclamation
        if (reclamation.getStatus() == StatusReclamation.PENDING) {
            holder.approvedButton.setVisibility(View.VISIBLE);
            holder.rejectedButton.setVisibility(View.VISIBLE);
        } else {
            holder.approvedButton.setVisibility(View.GONE);
            holder.rejectedButton.setVisibility(View.GONE);
        }

        // Handle button clicks to change status and send SMS
        holder.approvedButton.setOnClickListener(view -> {
            updateReclamation(holder.itemView.getContext(), reclamation, position, "APPROVE");
        });

        holder.rejectedButton.setOnClickListener(view -> {
            updateReclamation(holder.itemView.getContext(), reclamation, position, "REJECT");
        });
    }

    private void updateReclamation(Context context, Reclamation reclamation, int position, String action) {
        String message;

        if (action.equals("APPROVE")) {
            reclamation.setStatus(StatusReclamation.APPROVED);
            message = "Votre réclamation a été approuvée";
        } else {
            reclamation.setStatus(StatusReclamation.REJECTED);
            message = "Votre réclamation a été rejetée";
        }

        appDatabase.reclamationDAO().updateReclamation(reclamation);
        notifyItemChanged(position);

        // Send SMS Notification
        SmsUtils.sendSms(FROM_NUMBER, TO_NUMBER, message + " : " + reclamation.getDescription());

        Toast.makeText(context, message + " avec succès", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return reclamationList.size();
    }

    static class ReclamationViewHolder extends RecyclerView.ViewHolder {
        TextView typeTextView, descriptionTextView, statusTextView;
        Button approvedButton, rejectedButton;

        public ReclamationViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            approvedButton = itemView.findViewById(R.id.statusButtonAPPROVED);
            rejectedButton = itemView.findViewById(R.id.statusButtonREJECTED);
        }
    }
}
