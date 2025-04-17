public class Camion extends Vehicul {
    public Camion(long idVehicul, String nr, String model, Angajat angajat, String culoare) {
        super(idVehicul, nr, model, angajat, culoare);
    }

    @Override
    public double calculeazaPretInchiriere(int zile) {
        return zile * 100;
    }

    @Override
    public String getType() {
        return "Camion";
    }
}
