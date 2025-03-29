package src.main.java.lab4.ex3;

abstract class Reservation {
    protected final Customer customer;

    public Reservation(Customer customer) {
        this.customer = customer;
    }

    public abstract String getType();
    public abstract boolean reserve(CinemaHall cinemaHall);
}
