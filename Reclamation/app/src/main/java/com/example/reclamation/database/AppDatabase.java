package com.example.reclamation.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.reclamation.DAO.ReclamationDAO;
import com.example.reclamation.entities.Reclamation;

@Database(entities = {Reclamation.class},version = 2,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public  static  AppDatabase instance;
    public  abstract ReclamationDAO reclamationDAO();


    public static AppDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "reclamation")
                    .fallbackToDestructiveMigration() // Ajoutez cette ligne
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }



}
