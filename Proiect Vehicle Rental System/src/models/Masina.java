package models;

public class Masina extends Vehicul {
    public Masina(long idVehicul, String numarInmatriculare, String model, Angajat angajat, long idAngajat, String culoare, String tipVehicul) {
        super(idVehicul, numarInmatriculare, model, angajat, idAngajat, culoare, tipVehicul);
    }

    @Override
    public double calculPretInchiriere(int zile) {
        return 100.0 * zile; // Masina e mai scumpă decât default
    }
}
