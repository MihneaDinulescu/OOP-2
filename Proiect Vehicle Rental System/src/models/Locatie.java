package models;

import java.util.ArrayList;
import java.util.List;

public class Locatie {
    private long idLocatie;
    private String tara;
    private String oras;
    private String strada;
    private Integer nrStrada;
    private List<Angajat> angajati = new ArrayList<>();

    public Locatie(long idLocatie, String tara, String oras, String strada, Integer nrStrada) {
        this.idLocatie = idLocatie;
        this.tara = tara;
        this.oras = oras;
        this.strada = strada;
        this.nrStrada = nrStrada;
    }

    public long getIdLocatie() {
        return idLocatie;
    }

    public void setIdLocatie(long idLocatie) {
        this.idLocatie = idLocatie;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public Integer getNrStrada() {
        return nrStrada;
    }

    public void setNrStrada(Integer nrStrada) {
        this.nrStrada = nrStrada;
    }

    public List<Angajat> getAngajati() {
        return angajati;
    }

    public void adaugaAngajat(Angajat angajat) {
        angajati.add(angajat);
    }

    public void setAngajati(List<Angajat> angajati) {
        this.angajati = angajati;
    }
}
