import java.util.ArrayList;
import java.util.List;

public class Rezervare {
    private long idRezervare;
    private Client client;
    private double avans;
    private String metodaPlata;
    private List<RezervareVehicul> rezervareVehicule = new ArrayList<>();
    private List<Recenzie> recenzii = new ArrayList<>();

    public Rezervare(long idRezervare, Client client, double avans, String metodaPlata) {
        this.idRezervare = idRezervare;
        this.client = client;
        this.avans = avans;
        this.metodaPlata = metodaPlata;
    }

    public long getIdRezervare() {
        return idRezervare;
    }

    public Client getClient() {
        return client;
    }

    public double getAvans() {
        return avans;
    }

    public String getMetodaPlata() {
        return metodaPlata;
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
}
