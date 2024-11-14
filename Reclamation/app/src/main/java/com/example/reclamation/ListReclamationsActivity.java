package com.example.reclamation;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reclamation.database.AppDatabase;
import com.example.reclamation.entities.Reclamation;

import java.util.List;

public class ListReclamationsActivity extends AppCompatActivity {

    private RecyclerView reclamationsRecyclerView;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reclamations);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initializeUI();
        loadReclamations();
    }

    private void initializeUI() {
        // Setup RecyclerView
        reclamationsRecyclerView = findViewById(R.id.reclamationsRecyclerView);
        reclamationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadReclamations() {
        // Load the reclamations from the database
        appDatabase = AppDatabase.getAppDatabase(this); // Ensure database is initialized
        List<Reclamation> reclamations = appDatabase.reclamationDAO().getAllReclamations();
        ReclamationAdapter adapter = new ReclamationAdapter(reclamations, appDatabase); // Pass both the list and the database instance
        reclamationsRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Close the activity when the back arrow is pressed
        return true;
    }
}
