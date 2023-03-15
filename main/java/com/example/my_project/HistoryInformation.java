package com.example.my_project;

public class HistoryInformation {
    int id;
    String Etage;
    String Place;
    String Matricule;
    String Date;
    String Facture;
    public HistoryInformation(int id, String Etage, String Place, String Mtricule, String Date, String Facture){
        this.id = id;
        this.Etage = Etage;
        this.Place = Place;
        this.Matricule = Mtricule;
        this.Date = Date;
        this.Facture = Facture;
    }

    public int getId() {
        return id;
    }

    public String getEtage() {
        return Etage;
    }

    public String getPlace() {
        return Place;
    }

    public String getMatricule() {
        return Matricule;
    }

    public String getDate() {
        return Date;
    }

    public String getFacture() {
        return Facture;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setEtage(String etage) {
        Etage = etage;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public void setMatricule(String matricule) {
        Matricule = matricule;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setFacture(String facture) {
        Facture = facture;
    }
}
