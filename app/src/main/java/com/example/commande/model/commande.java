package com.example.commande.model;

public class commande {

    private int id;
    private String produit;
    private int quantite;
    private double prix;

    public commande(int id, String produit, int quantite, double prix) {
        this.id = id;
        this.produit = produit;
        this.quantite = quantite;
        this.prix = prix;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getProduit() { return produit; }
    public void setProduit(String produit) { this.produit = produit; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
}
