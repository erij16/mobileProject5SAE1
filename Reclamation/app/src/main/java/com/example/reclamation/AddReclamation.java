package com.example.reclamation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.reclamation.database.AppDatabase;
import com.example.reclamation.entities.Reclamation;
import com.example.reclamation.entities.StatusReclamation;
import com.example.reclamation.entities.TypeReclamation;

public class AddReclamation extends AppCompatActivity {

    private Spinner complaintTypeSpinner;
    private EditText complaintDescription;
    private Button buttonSubmit;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reclamation);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Add button in toolbar to view reclamations
        ImageButton viewReclamationsButton = findViewById(R.id.action_view_reclamations);
        viewReclamationsButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddReclamation.this, ListReclamationsClientActivity.class);
            startActivity(intent);
        });

        // Database and UI Initialization
        appDatabase = AppDatabase.getAppDatabase(this);
        initializeUI();
    }

    private void initializeUI() {
        complaintTypeSpinner = findViewById(R.id.complaintTypeSpinner);
        complaintDescription = findViewById(R.id.complaintDescription);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        ArrayAdapter<TypeReclamation> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, TypeReclamation.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complaintTypeSpinner.setAdapter(adapter);

        buttonSubmit.setOnClickListener(v -> submitComplaint());
    }

    private void submitComplaint() {
        String description = complaintDescription.getText().toString().trim();
        TypeReclamation selectedType = (TypeReclamation) complaintTypeSpinner.getSelectedItem();
        appDatabase.reclamationDAO().insert(new Reclamation(0, selectedType, description, StatusReclamation.PENDING));
        Toast.makeText(this, "Réclamation ajoutée avec succès", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddReclamation.this, ListReclamationsClientActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Close the activity when the back arrow is pressed
        return true;
    }
}
