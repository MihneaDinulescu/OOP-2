package src.main.java.lab4.ex3;

class VIPReservation extends Reservation {
    private final int col;

    public VIPReservation(Customer customer, int col) {
        super(customer);
        this.col = col;
    }

    @Override
    public String getType() {
        return "VIP";
    }

    @Override
    public boolean reserve(CinemaHall cinemaHall) {
        return cinemaHall.bookSeat(0, col, customer);
    }
}