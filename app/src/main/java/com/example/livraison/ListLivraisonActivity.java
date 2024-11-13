package com.example.livraison;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livraison.database.DatabaseHelper;
import com.example.livraison.model.livraison;

import java.util.ArrayList;
import java.util.List;

public class ListLivraisonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LivraisonAdapter livraisonAdapter;
    private DatabaseHelper databaseHelper;
    private List<livraison> livraisons;
    private Spinner statusSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_livraisons);

        // Initialize database and RecyclerView
        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewLivraisons);

        // Initialize the list of deliveries
        livraisons = databaseHelper.obtenirToutesLesLivraisons();

        // Check if there are any deliveries
        if (livraisons.isEmpty()) {
            Toast.makeText(this, "Aucune livraison trouvée", Toast.LENGTH_SHORT).show();
        }

        // Initialize the adapter and bind it to the RecyclerView
        livraisonAdapter = new LivraisonAdapter(this, livraisons);
        recyclerView.setAdapter(livraisonAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the Spinner for filtering status
        statusSpinner = findViewById(R.id.statusSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);

        // Add listener to Spinner for status filtering
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = parent.getItemAtPosition(position).toString();
                filterLivraisonsByStatus(selectedStatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Configure the Add Delivery button
        Button btnAjouterLivraison = findViewById(R.id.btnAjouterLivraison);
        btnAjouterLivraison.setOnClickListener(v -> {
            // Launch the Add Delivery activity
            Intent intent = new Intent(ListLivraisonActivity.this, AddLivraisonActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the delivery list after returning from the AddLivraisonActivity
        livraisons.clear();
        livraisons.addAll(databaseHelper.obtenirToutesLesLivraisons());
        livraisonAdapter.notifyDataSetChanged();
    }

    private void filterLivraisonsByStatus(String status) {
        List<livraison> filteredLivraisons;

        if (status.equals("All")) {
            // Show all deliveries
            filteredLivraisons = databaseHelper.obtenirToutesLesLivraisons();
        } else {
            // Show only deliveries with the selected status
            filteredLivraisons = new ArrayList<>();
            for (livraison liv : databaseHelper.obtenirToutesLesLivraisons()) {
                if (liv.getStatut().equals(status)) {
                    filteredLivraisons.add(liv);
                }
            }
        }

        // Update the adapter with the filtered list
        livraisons.clear();
        livraisons.addAll(filteredLivraisons);
        livraisonAdapter.notifyDataSetChanged();
    }
}
