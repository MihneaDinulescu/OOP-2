package models;

import java.util.Date;

public class RezervareVehicul {
    private long idRezervareVehicul;
    private Rezervare rezervare;
    private Vehicul vehicul;
    private long idRezervare;  // noul camp adaugat
    private long idVehicul;    // noul camp adaugat
    private Date startDate;
    private Date endDate;

    public RezervareVehicul(long idRezervareVehicul, Rezervare rezervare, Vehicul vehicul, long idRezervare, long idVehicul, Date startDate, Date endDate) {
        this.idRezervareVehicul = idRezervareVehicul;
        this.rezervare = rezervare;
        this.vehicul = vehicul;
        this.idRezervare = idRezervare;
        this.idVehicul = idVehicul;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getIdRezervareVehicul() {
        return idRezervareVehicul;
    }

    public Rezervare getRezervare() {
        return rezervare;
    }

    public void setRezervare(Rezervare rezervare) {
        this.rezervare = rezervare;
    }

    public Vehicul getVehicul() {
        return vehicul;
    }

    public void setVehicul(Vehicul vehicul) {
        this.vehicul = vehicul;
    }

    public long getIdRezervare() {
        return idRezervare;
    }

    public void setIdRezervare(long idRezervare) {
        this.idRezervare = idRezervare;
    }

    public long getIdVehicul() {
        return idVehicul;
    }

    public void setIdVehicul(long idVehicul) {
        this.idVehicul = idVehicul;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
