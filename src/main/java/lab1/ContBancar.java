package src.main.java.lab1;

public class ContBancar {
    private double sold;
    private final String iban;  // IBAN final

    public ContBancar(double soldInitial, String iban) {
        this.sold = soldInitial;
        this.iban = iban;
    }

    public void depunere(double suma) {
        sold += suma;
        System.out.println("Depunere de " + suma + " RON. Sold curent: " + sold + " RON.");
    }

    public void retragere(double suma) {
        if (suma <= sold) {
            sold -= suma;
            System.out.println("Retragere de " + suma + " RON. Sold curent: " + sold + " RON.");
        } else {
            System.out.println("Fonduri insuficiente pentru retragere.");
        }
    }

    public void verificaSold() {
        System.out.println("Soldul curent este: " + sold + " RON.");
    }

    public double getSold() {
        return sold;
    }

    public String getIban() {
        return iban;
    }
}
