package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Vehicul {
    private long idVehicul;
    private String numarInmatriculare;
    private String model;
    private Angajat angajat;
    private long idAngajat;  // noul camp adaugat
    private String culoare;
    private String tipVehicul; // nou câmp
    private List<RezervareVehicul> rezervariVehicul = new ArrayList<>();

    public Vehicul(long idVehicul, String numarInmatriculare, String model, Angajat angajat, long idAngajat, String culoare, String tipVehicul) {
        this.idVehicul = idVehicul;
        this.numarInmatriculare = numarInmatriculare;
        this.model = model;
        this.angajat = angajat;
        this.idAngajat = idAngajat;
        this.culoare = culoare;
        this.tipVehicul = tipVehicul;
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

    public void setAngajat(Angajat angajat) {
        this.angajat = angajat;
    }

    public long getIdAngajat() {
        return idAngajat;
    }

    public void setIdAngajat(long idAngajat) {
        this.idAngajat = idAngajat;
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

    public String getTipVehicul() {
        return tipVehicul;
    }

    public void setRezervari(List<RezervareVehicul> rezervari) {
        this.rezervariVehicul = rezervari;
    }

    public double calculPretInchiriere(int zile) {
        // Preț generic default
        return 50.0 * zile;
    }
}
