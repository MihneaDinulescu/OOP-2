package src.main.java.lab4.ex3;

class GroupReservation extends Reservation {
    private final int row, startCol, count;

    public GroupReservation(Customer customer, int row, int startCol, int count) {
        super(customer);
        this.row = row;
        this.startCol = startCol;
        this.count = count;
    }

    @Override
    public String getType() {
        return "Group";
    }

    @Override
    public boolean reserve(CinemaHall cinemaHall) {
        return cinemaHall.bookAdjacentSeats(row, startCol, count, customer);
    }
}