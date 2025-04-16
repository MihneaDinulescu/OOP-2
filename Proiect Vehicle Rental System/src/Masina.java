public class Masina extends Vehicul {
    public Masina(long idVehicul, String nr, String model, Angajat angajat, String culoare) {
        super(idVehicul, nr, model, angajat, culoare);
    }

    @Override
    public double calculeazaPretInchiriere(int zile) {
        return zile * 50;
    }

    @Override
    public String getType() {
        return "Masina";
    }
}
