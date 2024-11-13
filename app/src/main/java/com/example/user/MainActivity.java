package com.example.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.user.DataBase.DatabaseHelper;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper appDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        // Bouton de connexion (Login)
        MaterialButton loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            // Lancer l'activité de connexion (LoginActivity)
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Bouton d'inscription (Registration)
        MaterialButton signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(v -> {
            // Lancer l'activité d'inscription (RegistrationActivity)
            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
        // Nouveau bouton pour ouvrir la liste des utilisateurs
        MaterialButton userListButton = findViewById(R.id.userListButton);
        userListButton.setOnClickListener(v -> {
            // Lancer l'activité UserListActivity pour afficher la liste des utilisateurs
            Intent intent = new Intent(MainActivity.this, ListUserActivity.class);
            startActivity(intent);
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (appDataBase != null) {
            appDataBase.close();
        }
    }
}
