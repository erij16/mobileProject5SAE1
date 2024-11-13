package com.example.commande.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.commande.model.commande;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "commande.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_COMMANDE = "Commande";

    private static final String COL_ID = "id";
    private static final String COL_PRODUIT = "produit";
    private static final String COL_QUANTITE = "quantite";
    private static final String COL_PRIX = "prix";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_COMMANDE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PRODUIT + " TEXT, " +
                COL_QUANTITE + " INTEGER, " +
                COL_PRIX + " REAL)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDE);
        onCreate(db);
    }

    public long ajouterCommande(commande commande) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUIT, commande.getProduit());
        values.put(COL_QUANTITE, commande.getQuantite());
        values.put(COL_PRIX, commande.getPrix());

        long result = db.insert(TABLE_COMMANDE, null, values);
        db.close();
        return result;
    }

    public List<commande> obtenirToutesLesCommandes() {
        List<commande> listeCommandes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_COMMANDE, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String produit = cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUIT));
                int quantite = cursor.getInt(cursor.getColumnIndexOrThrow(COL_QUANTITE));
                double prix = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRIX));

                commande cmd = new commande(id, produit, quantite, prix);
                listeCommandes.add(cmd);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return listeCommandes;
    }

    public int mettreAJourCommande(commande commande) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUIT, commande.getProduit());
        values.put(COL_QUANTITE, commande.getQuantite());
        values.put(COL_PRIX, commande.getPrix());

        int rowsAffected = db.update(TABLE_COMMANDE, values, COL_ID + " = ?", new String[]{String.valueOf(commande.getId())});
        db.close();
        return rowsAffected;
    }

    public int supprimerCommande(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_COMMANDE, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }
}
