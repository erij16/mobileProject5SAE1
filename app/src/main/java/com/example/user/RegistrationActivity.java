package com.example.user;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.user.Api.RetrofitClient;
import com.example.user.Api.UserApi;
import com.example.user.Model.User;
import com.example.user.DataBase.DatabaseHelper;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private EditText usernameField, emailField, passwordField, repasswordField;
    private MaterialButton regBtn;
    private DatabaseHelper dbHelper;  // Local database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration); // Layout for registration

        // References to fields and button
        usernameField = findViewById(R.id.username);
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        repasswordField = findViewById(R.id.repassword); // Add reference to the confirm password field
        regBtn = findViewById(R.id.signupbtn);

        // Initialize the SQLite database
        dbHelper = new DatabaseHelper(this);

        // Action on register button click
        regBtn.setOnClickListener(v -> {
            String username = usernameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            String rePassword = repasswordField.getText().toString().trim();

            // Validate fields
            if (username.isEmpty()) {
                usernameField.setError("Enter a username");
                usernameField.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                emailField.setError("Enter an email");
                emailField.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailField.setError("Enter a valid email");
                emailField.requestFocus();
                return;
            }
            if (password.isEmpty() || password.length() < 6) {
                passwordField.setError("Password must be at least 6 characters");
                passwordField.requestFocus();
                return;
            }
            if (!password.equals(rePassword)) {
                repasswordField.setError("Passwords do not match");
                repasswordField.requestFocus();
                return;
            }

            // Create a User object
            User user = new User(username, email, password);

            // Add the user to the local database
            long result = dbHelper.addUser(user);
            if (result != -1) {
                Toast.makeText(RegistrationActivity.this, "User added to local database", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegistrationActivity.this, "Failed to add user to local database", Toast.LENGTH_SHORT).show();
            }

            // Call the API to register the user
            registerUser(user);
        });
    }

    private void registerUser(User user) {
        // Create an instance of the Retrofit API
        UserApi userApi = RetrofitClient.getInstance().create(UserApi.class);

        // Call the register method
        Call<Void> call = userApi.addUser(user);

        // Handle API response
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Success
                    Toast.makeText(RegistrationActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    finish();  // End the registration activity
                } else {
                    // Server or other error
                    Toast.makeText(RegistrationActivity.this, "Registration failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Connection or other error
                Toast.makeText(RegistrationActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
