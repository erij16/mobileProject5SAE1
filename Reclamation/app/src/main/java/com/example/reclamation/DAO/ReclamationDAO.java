package com.example.reclamation.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.reclamation.entities.Reclamation;

import java.util.List;

@Dao
public interface ReclamationDAO {

    @Insert
    void insert(Reclamation reclamation);

    @Query("SELECT * FROM reclamations")
    List<Reclamation> getAllReclamations();

    @Update
    void updateReclamation(Reclamation reclamation);

    @Delete
    void deleteReclamation(Reclamation reclamation);

}
