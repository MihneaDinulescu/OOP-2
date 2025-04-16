import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Vehicul implements Rentabil {
    private long idVehicul; // ID unic pentru vehicul
    private String numarInmatriculare;
    private String model;
    private Angajat angajat;
    private String culoare; // Adăugarea câmpului pentru culoare
    private List<RezervareVehicul> rezervariVehicul = new ArrayList<>();

    public Vehicul(long idVehicul, String numarInmatriculare, String model, Angajat angajat, String culoare) {
        this.idVehicul = idVehicul;
        this.numarInmatriculare = numarInmatriculare;
        this.model = model;
        this.angajat = angajat;
        this.culoare = culoare;
    }

    public long getIdVehicul() {
        return idVehicul;
    }

    public String getNumarInmatriculare() {
        return numarInmatriculare;
    }

    public String getModel() {
        return model;
    }

    public Angajat getAngajat() {
        return angajat;
    }

    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public List<RezervareVehicul> getRezervariVehicul() {
        return rezervariVehicul;
    }

    public boolean isDisponibil(Date dataStart, Date dataEnd) {
        for (RezervareVehicul rezervareVehicul : rezervariVehicul) {
            if (!(dataEnd.before(rezervareVehicul.getStartDate()) || dataStart.after(rezervareVehicul.getEndDate()))) {
                return false;
            }
        }
        return true;
    }


    public abstract String getType();
}
