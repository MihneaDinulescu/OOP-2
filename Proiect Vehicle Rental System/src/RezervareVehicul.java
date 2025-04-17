import java.util.Date;

public class RezervareVehicul {
    private long idRezervareVehicul;
    private Rezervare rezervare;
    private Vehicul vehicul;
    private Date startDate;
    private Date endDate;

    public RezervareVehicul(long idRezervareVehicul, Rezervare rezervare, Vehicul vehicul, Date startDate, Date endDate) {
        this.idRezervareVehicul = idRezervareVehicul;
        this.rezervare = rezervare;
        this.vehicul = vehicul;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getIdRezervareVehicul() {
        return idRezervareVehicul;
    }

    public Rezervare getRezervare() {
        return rezervare;
    }

    public Vehicul getVehicul() {
        return vehicul;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
