package src.main.java.lab1;

public class User {
    private final String nume;
    private final char gen;
    private final String cnp;  // CNP final
    private ContBancar cont;

    // Constructor
    public User(String nume, char gen, String cnp, ContBancar cont) {
        this.nume = nume;
        this.gen = gen;
        this.cnp = cnp;
        this.cont = cont;
    }


    public void depune(double suma) {
        cont.depunere(suma);
    }

    public void retrage(double suma) {
        cont.retragere(suma);
    }

    public void verificaSold() {
        cont.verificaSold();
    }


    public String getNume() {
        return nume;
    }


    public char getGen() {
        return gen;
    }


    public String getCnp() {
        return cnp;
    }


    public String getIban() {
        return cont.getIban();
    }


    public void setCont(ContBancar cont) {
        this.cont = cont;
    }
}
