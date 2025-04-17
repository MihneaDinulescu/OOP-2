public class Bicicleta extends Vehicul {
    public Bicicleta(long idVehicul, String nr, String model, Angajat angajat, String culoare) {
        super(idVehicul, nr, model, angajat, culoare);
    }

    @Override
    public double calculeazaPretInchiriere(int zile) {
        return zile * 20;
    }

    @Override
    public String getType() {
        return "Bicicleta";
    }
}
