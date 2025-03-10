package src.main.java.lab1;

public class ContCurent extends ContBancar {
    public ContCurent(double soldInitial, String iban) {
        super(soldInitial, iban);
    }

    @Override
    public void retragere(double suma) {
        super.retragere(suma);
        System.out.println("Retragere realizată cu succes din contul curent in valoare de: " + suma + " RON.");
    }
}
