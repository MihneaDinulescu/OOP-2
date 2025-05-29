package models;

import java.util.ArrayList;
import java.util.List;

public class Rezervare {
    private long idRezervare;
    private Client client;
    private long idClient;
    private double avans;
    private String metodaPlata;
    private List<RezervareVehicul> rezervareVehicule = new ArrayList<>();
    private List<Recenzie> recenzii = new ArrayList<>();

    public Rezervare(long idRezervare, Client client, long idClient, double avans, String metodaPlata) {
        this.idRezervare = idRezervare;
        this.client = client;
        this.idClient = idClient;
        this.avans = avans;
        this.metodaPlata = metodaPlata;
    }

    public long getIdRezervare() {
        return idRezervare;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }

    public double getAvans() {
        return avans;
    }

    public void setAvans(double avans) {
        this.avans = avans;
    }

    public String getMetodaPlata() {
        return metodaPlata;
    }

    public void setMetodaPlata(String metodaPlata) {
        this.metodaPlata = metodaPlata;
    }

    public List<RezervareVehicul> getRezervareVehicule() {
        return rezervareVehicule;
    }

    public void adaugaRezervareVehicul(RezervareVehicul rv) {
        rezervareVehicule.add(rv);
    }

    public List<Recenzie> getRecenzii() {
        return recenzii;
    }

    public void adaugaRecenzie(Recenzie recenzie) {
        recenzii.add(recenzie);
    }

    public void setRecenzii(List<Recenzie> recenzii) {
        this.recenzii= recenzii;
    }

    public void setRezervariVehicul(List<RezervareVehicul> rezervariVehicul) {
        this.rezervareVehicule = rezervariVehicul;
    }
}
