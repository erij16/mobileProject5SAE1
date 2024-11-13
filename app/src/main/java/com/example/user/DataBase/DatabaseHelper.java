package com.example.user.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.user.Model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "User";

    private static final String COL_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_USER + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    // Ajouter un utilisateur
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, user.getUsername());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_PASSWORD, user.getPassword());

        long result = -1;
        try {
            result = db.insert(TABLE_USER, null, values);
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error adding user", e);
        } finally {
            db.close();
        }
        return result;
    }

    // Obtenir tous les utilisateurs
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_USER, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    User user = new User(
                            cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_USERNAME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD))
                    );
                    users.add(user);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error fetching users", e);
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return users;
    }

    // Mettre à jour un utilisateur
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, user.getUsername());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_PASSWORD, user.getPassword());

        int rowsAffected = 0;
        try {
            rowsAffected = db.update(TABLE_USER, values, COL_ID + " = ?",
                    new String[]{String.valueOf(user.getId())});
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error updating user", e);
        } finally {
            db.close();
        }
        return rowsAffected;
    }

    // Supprimer un utilisateur
    public int deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = 0;
        try {
            rowsDeleted = db.delete(TABLE_USER, COL_ID + " = ?", new String[]{String.valueOf(id)});
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error deleting user", e);
        } finally {
            db.close();
        }
        return rowsDeleted;
    }

    // Obtenir un utilisateur par ID
    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;
        Cursor cursor = db.query(TABLE_USER, null, COL_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD))
            );
            cursor.close();
        }
        db.close();
        return user;
    }

    // Obtenir un utilisateur par email et mot de passe
    public User getUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;
        Cursor cursor = db.query(TABLE_USER, null, COL_EMAIL + " = ? AND " + COL_PASSWORD + " = ?",
                new String[]{email, password}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD))
            );
            cursor.close();
        }
        db.close();
        return user;
    }
}
