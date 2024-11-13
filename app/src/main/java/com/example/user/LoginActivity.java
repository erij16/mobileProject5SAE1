package com.example.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.user.DataBase.DatabaseHelper;

import com.example.user.Model.User;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper appDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisation de la base de données avec l'instance singleton
        appDataBase = new DatabaseHelper(this);

        EditText emailField = findViewById(R.id.username);
        EditText passwordField = findViewById(R.id.password);
        MaterialButton loginBtn = findViewById(R.id.loginbtn);

        // Gestion du clic sur le bouton de connexion
        loginBtn.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            // Vérification des champs vides
            if (email.isEmpty()) {
                emailField.setError("Entrez un e-mail");
                emailField.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                passwordField.setError("Entrez un mot de passe");
                passwordField.requestFocus();
                return;
            }

            // Connexion avec la base de données
            User user = appDataBase.getUser(email, password);
            if (user != null) {
                Toast.makeText(LoginActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Fermer LoginActivity pour éviter qu'il reste dans l'historique de la pile
            } else {
                Toast.makeText(LoginActivity.this, "Échec de la connexion : e-mail ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
            }
        });

        // Rediriger vers l'écran d'inscription
        TextView signUpLink = findViewById(R.id.signUpLink);
        signUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }

}
