package com.example.produitsprimairesf.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.produitsprimairesf.DAO.ProductDAO;
import com.example.produitsprimairesf.entities.ProductP;

@Database(entities = {ProductP.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase instance;
    public abstract ProductDAO productDAO();

    public static AppDatabase getAppDatabase(Context context){
        context.deleteDatabase("app-stock");

        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"app-stock").allowMainThreadQueries().build();
        }
        return instance;
    }
    public static void clearInstance() {
        instance = null;
    }

}
