package com.example.user;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.user.DataBase.DatabaseHelper;
import com.example.user.Model.User;

public class UpdateUserActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextEmail, editTextPassword;
    private Button buttonUpdateUser;
    private int userId;
    private DatabaseHelper appDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        // Initialisation des éléments de l'interface utilisateur
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonUpdateUser = findViewById(R.id.buttonUpdateUser);

        // Initialiser la base de données
        appDataBase = new DatabaseHelper(this);

        // Récupérer l'ID et les données de l'utilisateur à partir de l'intent
        userId = getIntent().getIntExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Utilisateur non valide", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");

        // Afficher les valeurs de l'utilisateur dans les champs
        editTextUsername.setText(username);
        editTextEmail.setText(email);
        editTextPassword.setText(password);

        // Mettre à jour l'utilisateur
        buttonUpdateUser.setOnClickListener(v -> {
            String newUsername = editTextUsername.getText().toString().trim();
            String newEmail = editTextEmail.getText().toString().trim();
            String newPassword = editTextPassword.getText().toString().trim();

            if (newUsername.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
                return;
            }

            // Créer l'utilisateur mis à jour avec l'ID
            User updatedUser = new User(userId, newUsername, newEmail, newPassword);

            // Appeler la méthode pour mettre à jour l'utilisateur
            int rowsUpdated = appDataBase.updateUser(updatedUser);

            if (rowsUpdated > 0) {
                Toast.makeText(this, "Utilisateur mis à jour avec succès", Toast.LENGTH_SHORT).show();
                finish();  // Retourner à l'écran précédent après la mise à jour
            } else {
                Toast.makeText(this, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
            }
        });
    }
}