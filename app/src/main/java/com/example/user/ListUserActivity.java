package com.example.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.user.Model.User;
import com.example.user.DataBase.DatabaseHelper;
import com.example.user.UpdateUserActivity;

import java.util.List;

public class ListUserActivity extends AppCompatActivity {

    private ListView listViewUsers;
    private DatabaseHelper databaseHelper;
    private List<User> users;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        listViewUsers = findViewById(R.id.listViewUsers);
        databaseHelper = new DatabaseHelper(this);

        // Charger les utilisateurs depuis la base de données
        users = databaseHelper.getAllUsers();

        // Créer un adaptateur pour la ListView
        userAdapter = new UserAdapter();
        listViewUsers.setAdapter(userAdapter);

        // Gestion des clics sur la ListView
        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
                // On peut gérer ici les clics sur les éléments si nécessaire
            }
        });
    }

    // Adapter pour afficher les utilisateurs et gérer les actions sur les boutons
    private class UserAdapter extends ArrayAdapter<User> {

        public UserAdapter() {
            super(ListUserActivity.this, R.layout.user_item, users); // Utilisation de user_item.xml
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.user_item, parent, false); // Gonfler le layout user_item.xml
            }

            User user = users.get(position);

            // Récupérer les vues
            TextView usernameTextView = view.findViewById(R.id.textViewUsername);
            TextView emailTextView = view.findViewById(R.id.textViewEmail);
            Button updateButton = view.findViewById(R.id.buttonUpdateUser);
            Button deleteButton = view.findViewById(R.id.buttonDelete);

            // Afficher les informations de l'utilisateur
            usernameTextView.setText(user.getUsername());
            emailTextView.setText(user.getEmail());

            // Action du bouton Update
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ouvrir l'écran de mise à jour de l'utilisateur
                    Intent intent = new Intent(ListUserActivity.this, UpdateUserActivity.class);
                    intent.putExtra("userId", user.getId());  // Passer l'ID de l'utilisateur
                    intent.putExtra("username", user.getUsername());  // Passer le nom d'utilisateur
                    intent.putExtra("email", user.getEmail());  // Passer l'email
                    intent.putExtra("password", user.getPassword());  // Passer le mot de passe
                    startActivity(intent);
                }
            });


            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int rowsDeleted = databaseHelper.deleteUser(user.getId());
                    if (rowsDeleted > 0) {
                        users.remove(position);
                        userAdapter.notifyDataSetChanged();  // Rafraîchir la liste
                        Toast.makeText(ListUserActivity.this, "Utilisateur supprimé", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ListUserActivity.this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            return view;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Recharger les utilisateurs depuis la base de données
        users.clear();  // Nettoyer la liste existante
        users.addAll(databaseHelper.getAllUsers());  // Ajouter les utilisateurs mis à jour
        userAdapter.notifyDataSetChanged();  // Notifier l'adaptateur pour rafraîchir la ListView
    }
}
