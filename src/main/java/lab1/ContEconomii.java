package src.main.java.lab1;

public class ContEconomii extends ContBancar {
    private static final double COMISION = 1.5;
    public ContEconomii(double soldInitial, String iban) {
        super(soldInitial, iban);
    }

    @Override
    public void retragere(double suma) {
        double sumaTotala = suma + COMISION;
        if (sumaTotala <= getSold()) {
            super.retragere(sumaTotala);
            System.out.println("S-a aplicat comisionul de retragere de " + COMISION + " RON.");
        } else {
            System.out.println("Fonduri insuficiente pentru retragere în contul de economii.");
        }
    }
}
