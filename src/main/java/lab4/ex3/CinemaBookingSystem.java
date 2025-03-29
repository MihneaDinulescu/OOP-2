package src.main.java.lab4.ex3;

public class CinemaBookingSystem {
    public static void main(String[] args) {
        CinemaHall hall = new CinemaHall(5, 5); // 5x5 Cinema Hall
        BookingPlanner planner = new BookingPlanner(hall, 15); // Max 15 reservations

        Customer alice = new Customer("Alex", "alex@example.com");
        Customer bob = new Customer("Andrew", "andrew@example.com");
        Customer charlie = new Customer("Carla", "carla@example.com");
        Customer luca = new Customer("Luca", "luca@example.com");

        planner.addReservation(new RegularReservation(alice, 2, 2));
        planner.addReservation(new VIPReservation(bob, 1));
        planner.addReservation(new RegularReservation(charlie, 3, 3));
        planner.addReservation(new GroupReservation(luca, 4, 0, 3));

        planner.processReservations();
        planner.printSeatingMap();
        hall.listBookedSeats();
    }
}
