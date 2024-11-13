package com.example.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.user.Model.User;

import java.util.List;

public class UserAdapter extends BaseAdapter {

    private final Context context;
    private final List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_update_user, parent, false);
        }

        User user = userList.get(position);

        TextView userName = convertView.findViewById(R.id.username);
        TextView userEmail = convertView.findViewById(R.id.email);
        Button updateButton = convertView.findViewById(R.id.buttonUpdateUser);
        Button deleteButton = convertView.findViewById(R.id.buttonDelete);

        userName.setText(user.getUsername());
        userEmail.setText(user.getEmail());

        // Bouton pour mettre à jour l'utilisateur
        updateButton.setOnClickListener(v -> {
            // Logique pour mettre à jour l'utilisateur
            Toast.makeText(context, "Update " + user.getUsername(), Toast.LENGTH_SHORT).show();
        });

        // Bouton pour supprimer l'utilisateur
        deleteButton.setOnClickListener(v -> {
            // Logique pour supprimer l'utilisateur
            userList.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "Deleted " + user.getUsername(), Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
