package src.main.java.lab4.ex3;

class RegularReservation extends Reservation {
    private final int row, col;

    public RegularReservation(Customer customer, int row, int col) {
        super(customer);
        this.row = row;
        this.col = col;
    }

    @Override
    public String getType() {
        return "Regular";
    }

    @Override
    public boolean reserve(CinemaHall cinemaHall) {
        return cinemaHall.bookSeat(row, col, customer);
    }
}