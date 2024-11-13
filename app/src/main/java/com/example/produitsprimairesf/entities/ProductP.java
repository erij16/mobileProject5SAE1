package com.example.produitsprimairesf.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName="ProductP")
public class ProductP implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id ;

    @ColumnInfo()
    private String code;
    @ColumnInfo()
    private String nom;
    @ColumnInfo()
    private int quantite;
    @ColumnInfo()
    private double prix_unitaire;
    @ColumnInfo()
    private int seuil_min;
    @ColumnInfo(name = "image_uri")
    private String imageUri;

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }



    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    public int getSeuil_min() {
        return seuil_min;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public void setSeuil_min(int seuil_min) {
        this.seuil_min = seuil_min;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Ignore
    public ProductP(int id, String code, String nom, int quantite, double prix_unitaire, int seuil_min) {
        this.id = id;
        this.code = code;
        this.nom = nom;
        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;
        this.seuil_min = seuil_min;
    }

    public ProductP(String code, String nom, int quantite, double prix_unitaire, int seuil_min) {
        this.nom = nom;
        this.code = code;
        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;
        this.seuil_min = seuil_min;
    }

    @Override
    public String toString() {
        return "ProductP{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", nom='" + nom + '\'' +
                ", quantite=" + quantite +
                ", prix_unitaire=" + prix_unitaire +
                ", seuil_min=" + seuil_min +
                '}';
    }
}
