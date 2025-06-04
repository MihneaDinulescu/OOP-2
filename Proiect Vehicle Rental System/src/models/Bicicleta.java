package models;

public class Bicicleta extends Vehicul {
    public Bicicleta(long idVehicul, String numarInmatriculare, String model, Angajat angajat, long idAngajat, String culoare, String tipVehicul) {
        super(idVehicul, numarInmatriculare, model, angajat, idAngajat, culoare, tipVehicul);
    }

    @Override
    public double calculPretInchiriere(int zile) {
        return 15.0 * zile;
    }
}