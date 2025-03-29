package src.main.java.lab4.ex3;

record Seat(boolean booked, String label, Customer bookedBy) {
    public Seat book(Customer customer) {
        return new Seat(true, label, customer);
    }
}