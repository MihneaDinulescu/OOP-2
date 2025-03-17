package src.main.java.lab2;

import java.util.Objects;

class Angajat {
    private String nume;
    private String prenume;
    private int anulAngajarii;
    private int salariu;
    private Departament departament;

    public Angajat(String nume, String prenume, int anulAngajarii, int salariu, Departament departament) {
        this.nume = nume;
        this.prenume = prenume;
        this.anulAngajarii = anulAngajarii;
        this.salariu = salariu;
        this.departament = departament;
        this.departament.adaugaAngajat(this);
    }

    public String getNumeComplet() {
        return nume + " " + prenume;
    }

    public int getSalariu() {
        return salariu;
    }

    public void setSalariu(int salariu) {
        this.salariu = salariu;
    }

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Angajat angajat = (Angajat) o;
        return anulAngajarii == angajat.anulAngajarii &&
                salariu == angajat.salariu &&
                Objects.equals(nume, angajat.nume) &&
                Objects.equals(prenume, angajat.prenume) &&
                Objects.equals(departament, angajat.departament);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, prenume, anulAngajarii, salariu, departament);
    }
}
