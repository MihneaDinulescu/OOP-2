package src.main.java.lab2;

class ManagementService {
    private static ManagementService instance;

    private ManagementService() {}

    public static ManagementService getInstance() {
        if (instance == null) {
            instance = new ManagementService();
        }
        return instance;
    }

    public void inregistreazaAngajat(String nume, String prenume, int anulAngajarii, int salariu, Departament departament) {
        new Angajat(nume, prenume, anulAngajarii, salariu, departament);
    }

    public void schimbaDepartament(Angajat angajat, Departament departamentNou) {
        Departament departamentVechi = angajat.getDepartament();
        departamentVechi.stergeAngajat(angajat);
        departamentNou.adaugaAngajat(angajat);
        angajat.setDepartament(departamentNou);
    }

    public void schimbaSalariu(Angajat angajat, int salariuNou) {
        angajat.setSalariu(salariuNou);
    }

    public void afiseazaDepartamente() {
        Departamente.afiseazaDepartamente();
    }

    public void afiseazaMembriDinFiecareDepartament() {
        for (Departament departament : Departamente.getDepartamente()) {
            System.out.println("Departamentul " + departament.getNumeDepartament() + " are " + departament.getNumarAngajati() + " angajați.");
            departament.afiseazaAngajati();
        }
    }

    public void afiseazaSalariuMediuDinDepartament(Departament departament) {
        double salariuMediu = departament.calculareSalariuMediu();
        System.out.println("Salariul mediu din departamentul " + departament.getNumeDepartament() + " este: " + salariuMediu);
    }
}

