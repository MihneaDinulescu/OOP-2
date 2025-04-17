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

    // Getters
    public long getIdClient() {
        return idClient;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public String getEmail() {
        return email;
    }

    public List<Rezervare> getRezervari() {
        return rezervari;
    }

    public void adaugaRezervare(Rezervare rezervare) {
        rezervari.add(rezervare);
    }
}
