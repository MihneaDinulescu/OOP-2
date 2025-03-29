package src.main.java.lab4.ex3;

class BookingPlanner implements Bookable {
    private final Reservation[] reservations;
    private final CinemaHall cinemaHall;
    private int reservationCount;

    public BookingPlanner(CinemaHall cinemaHall, int maxReservations) {
        this.cinemaHall = cinemaHall;
        this.reservations = new Reservation[maxReservations];
        this.reservationCount = 0;
    }

    public void addReservation(Reservation reservation) {
        if (reservationCount < reservations.length) {
            reservations[reservationCount++] = reservation;
        }
    }

    @Override
    public void processReservations() {
        for (int i = 0; i < reservationCount; i++) {
            reservations[i].reserve(cinemaHall);
        }
    }

    @Override
    public void printSeatingMap() {
        StringBuilder sb = new StringBuilder();
        Seat[][] seats = cinemaHall.getSeats();
        for (int i = 0; i < seats.length; i++) {
            sb.append("Row ").append((char) ('A' + i)).append(": ");
            for (Seat seat : seats[i]) {
                sb.append(seat.booked() ? "[X]" : "[ ]").append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }
}