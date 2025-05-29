package models;

import java.util.Date;

public class Recenzie {
    private long idRecenzie;
    private int nrStele;
    private Date dataRecenzie;
    private Rezervare rezervare;

    public Recenzie(long idRecenzie, int nrStele, Date dataRecenzie, Rezervare rezervare) {
        this.idRecenzie = idRecenzie;
        this.nrStele = nrStele;
        this.dataRecenzie = dataRecenzie;
        this.rezervare = rezervare;
    }

    public long getIdRecenzie() {
        return idRecenzie;
    }

    public int getNrStele() {
        return nrStele;
    }

    public Date getDataRecenzie() {
        return dataRecenzie;
    }

    public Rezervare getRezervare() {
        return rezervare;
    }
}
