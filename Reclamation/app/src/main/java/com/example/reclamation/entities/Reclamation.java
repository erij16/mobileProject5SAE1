package com.example.reclamation.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "reclamations")
public class Reclamation {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo()
    private TypeReclamation type;

    @ColumnInfo()
    private String description;

    @ColumnInfo()
    private int userId; // L'ID de l'utilisateur sera l'ID de l'utilisateur statique

    private StatusReclamation status;

    // Constructeur
    public Reclamation(long id, TypeReclamation type, String description, StatusReclamation status) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.status = status;
        this.userId = User.DEFAULT_USER.getId(); // Assigner l'ID de l'utilisateur statique
    }

    // Getters et Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TypeReclamation getType() {
        return type;
    }

    public void setType(TypeReclamation type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusReclamation getStatus() {
        return status;
    }

    public void setStatus(StatusReclamation status) {
        this.status = status;
    }

    public int getUserId() { // Ajoutez ce getter
        return userId;
    }

    public void setUserId(int userId) { // Ajoutez ce setter
        this.userId = userId;
    }
}
