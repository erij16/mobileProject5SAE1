package com.example.livraison.model;

public class livraison {

    private int id;
    private String adresseDepart;
    private String adresseDestination;
    private String statut;
    private String dateLivraison;


    public livraison(int id, String adresseDepart, String adresseDestination, String statut, String dateLivraison) {
        this.id = id;
        this.adresseDepart = adresseDepart;
        this.adresseDestination = adresseDestination;
        this.statut = statut;
        this.dateLivraison = dateLivraison;

    }
    public livraison(String adresseDepart, String adresseDestination, String statut, String dateLivraison) {
        this.adresseDepart = adresseDepart;
        this.adresseDestination = adresseDestination;
        this.statut = statut;
        this.dateLivraison = dateLivraison;
    }

    public livraison() {

    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAdresseDepart() { return adresseDepart; }
    public void setAdresseDepart(String adresseDepart) { this.adresseDepart = adresseDepart; }
    public String getAdresseDestination() { return adresseDestination; }
    public void setAdresseDestination(String adresseDestination) { this.adresseDestination = adresseDestination; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public String getDateLivraison() { return dateLivraison; }
    public void setDateLivraison(String dateLivraison) { this.dateLivraison = dateLivraison; }

}
