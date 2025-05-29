package models;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private long idClient;
    private String nume;
    private String prenume;
    private String numarTelefon;
    private String email;
    private List<Rezervare> rezervari = new ArrayList<>();

    public Client(long idClient, String nume, String prenume, String numarTelefon, String email) {
        this.idClient = idClient;
        this.nume = nume;
        this.prenume = prenume;
        this.numarTelefon = numarTelefon;
        this.email = email;
    }

    // Getters and setters
    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {  // Adăugăm metoda setNume
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Rezervare> getRezervari() {
        return rezervari;
    }

    public void adaugaRezervare(Rezervare rezervare) {
        rezervari.add(rezervare);
    }

    public void setRezervari(List<Rezervare> rezervari) {
        this.rezervari = rezervari;
    }
}
