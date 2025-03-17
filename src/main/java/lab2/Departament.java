package src.main.java.lab2;

import java.util.ArrayList;
import java.util.List;

class Departament {
    private final String numeDepartament;
    private int numarAngajati;
    private List<Angajat> membri = new ArrayList<>();

    public Departament(String numeDepartament) {
        this.numeDepartament = numeDepartament;
        this.numarAngajati = 0;
    }

    public String getNumeDepartament() {
        return numeDepartament;
    }

    public void adaugaAngajat(Angajat angajat) {
        if (!membri.contains(angajat)) {
            membri.add(angajat);
            numarAngajati++;
        } else {
            System.out.println("Angajatul " + angajat.getNumeComplet() + " este deja în acest departament.");
        }
    }

    public void stergeAngajat(Angajat angajat) {
        if (membri.contains(angajat)) {
            membri.remove(angajat);
            numarAngajati--;
        }
    }

    public void afiseazaAngajati() {
        System.out.println("Angajații din departamentul " + numeDepartament + ":");
        for (Angajat angajat : membri) {
            System.out.println(angajat.getNumeComplet());
        }
    }

    public int getNumarAngajati() {
        return numarAngajati;
    }

    public List<Angajat> getMembri() {
        return membri;
    }

    public double calculareSalariuMediu() {
        if (numarAngajati == 0) return 0;

        int totalSalarii = 0;
        for (Angajat angajat : membri) {
            totalSalarii += angajat.getSalariu();
        }
        return (double) totalSalarii / numarAngajati;
    }
}
