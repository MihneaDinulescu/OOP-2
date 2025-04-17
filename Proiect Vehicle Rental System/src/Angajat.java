import java.util.ArrayList;
import java.util.List;

public class Angajat {
    private long idAngajat;
    private String numeAngajat;
    private String prenumeAngajat;
    private String nrTelefon;
    private String emailAngajat;
    private String functie;
    private Locatie locatie;
    private List<Vehicul> vehicule = new ArrayList<>();

    public Angajat(long idAngajat, String numeAngajat, String prenumeAngajat, String nrTelefon,
                   String emailAngajat, String functie, Locatie locatie) {
        this.idAngajat = idAngajat;
        this.numeAngajat = numeAngajat;
        this.prenumeAngajat = prenumeAngajat;
        this.nrTelefon = nrTelefon;
        this.emailAngajat = emailAngajat;
        this.functie = functie;
        this.locatie = locatie;
    }

    public long getIdAngajat() {
        return idAngajat;
    }

    public void setIdAngajat(long idAngajat) {
        this.idAngajat = idAngajat;
    }

    public String getNumeAngajat() {
        return numeAngajat;
    }

    public void setNumeAngajat(String numeAngajat) {
        this.numeAngajat = numeAngajat;
    }

    public String getPrenumeAngajat() {
        return prenumeAngajat;
    }

    public void setPrenumeAngajat(String prenumeAngajat) {
        this.prenumeAngajat = prenumeAngajat;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

    public String getEmailAngajat() {
        return emailAngajat;
    }

    public void setEmailAngajat(String emailAngajat) {
        this.emailAngajat = emailAngajat;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public Locatie getLocatie() {
        return locatie;
    }

    public void setLocatie(Locatie locatie) {
        this.locatie = locatie;
    }

    public List<Vehicul> getVehicule() {
        return vehicule;
    }

    public void adaugaVehicul(Vehicul vehicul) {
        vehicule.add(vehicul);
    }
}
