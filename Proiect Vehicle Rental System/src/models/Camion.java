package models;

public class Camion extends Vehicul {
    public Camion(long idVehicul, String numarInmatriculare, String model, Angajat angajat, long idAngajat, String culoare, String tipVehicul) {
        super(idVehicul, numarInmatriculare, model, angajat, idAngajat, culoare, tipVehicul);
    }

    @Override
    public double calculPretInchiriere(int zile) {
        return 200.0 * zile; // Camionul e mai scump
    }
}
