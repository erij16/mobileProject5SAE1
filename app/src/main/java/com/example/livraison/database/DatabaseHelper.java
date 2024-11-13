package com.example.livraison.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.livraison.model.livraison;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "livraison.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_LIVRAISON = "Livraison";

    private static final String COL_ID = "id";
    private static final String COL_ADRESSE_DEPART = "adresseDepart";
    private static final String COL_ADRESSE_DESTINATION = "adresseDestination";
    private static final String COL_STATUT = "statut";
    private static final String COL_DATE_LIVRAISON = "dateLivraison";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_LIVRAISON + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ADRESSE_DEPART + " TEXT, " +
                COL_ADRESSE_DESTINATION + " TEXT, " +
                COL_STATUT + " TEXT, " +
                COL_DATE_LIVRAISON + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVRAISON);
        onCreate(db);
    }

    // CRUD Operations:
    public long ajouterLivraison(livraison livraison) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ADRESSE_DEPART, livraison.getAdresseDepart());
        values.put(COL_ADRESSE_DESTINATION, livraison.getAdresseDestination());
        values.put(COL_STATUT, livraison.getStatut());
        values.put(COL_DATE_LIVRAISON, livraison.getDateLivraison());

        long result = -1;
        try {
            result = db.insert(TABLE_LIVRAISON, null, values);
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error adding livraison", e);
        } finally {
            db.close();
        }
        return result;
    }

    public List<livraison> obtenirToutesLesLivraisons() {
        List<livraison> listeLivraisons = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_LIVRAISON, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    livraison liv = new livraison(
                            cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_ADRESSE_DEPART)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_ADRESSE_DESTINATION)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE_LIVRAISON))
                    );
                    listeLivraisons.add(liv);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error fetching livraisons", e);
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return listeLivraisons;
    }

    public int mettreAJourLivraison(livraison livraison) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ADRESSE_DEPART, livraison.getAdresseDepart());
        values.put(COL_ADRESSE_DESTINATION, livraison.getAdresseDestination());
        values.put(COL_STATUT, livraison.getStatut());
        values.put(COL_DATE_LIVRAISON, livraison.getDateLivraison());

        int rowsAffected = 0;
        try {
            rowsAffected = db.update(TABLE_LIVRAISON, values, COL_ID + " = ?",
                    new String[]{String.valueOf(livraison.getId())});
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error updating livraison", e);
        } finally {
            db.close();
        }
        return rowsAffected;
    }

    public int supprimerLivraison(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = 0;
        try {
            rowsDeleted = db.delete(TABLE_LIVRAISON, COL_ID + " = ?", new String[]{String.valueOf(id)});
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error deleting livraison", e);
        } finally {
            db.close();
        }
        return rowsDeleted;
    }
    public livraison obtenirLivraisonParId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LIVRAISON, null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            livraison liv = new livraison();
            try {
                liv.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                liv.setAdresseDepart(cursor.getString(cursor.getColumnIndexOrThrow("adresseDepart")));
                liv.setAdresseDestination(cursor.getString(cursor.getColumnIndexOrThrow("adresseDestination")));
                liv.setStatut(cursor.getString(cursor.getColumnIndexOrThrow("statut")));
                liv.setDateLivraison(cursor.getString(cursor.getColumnIndexOrThrow("dateLivraison")));
            } catch (IllegalArgumentException e) {
                Log.e("DatabaseError", "Column not found: " + e.getMessage());
            }
            cursor.close();
            return liv;
        }
        cursor.close();
        return null;
    }

    // DatabaseHelper.java


}
