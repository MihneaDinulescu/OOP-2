package src.main.java.lab4.ex3;

class CinemaHall {
    private final Seat[][] seats;
    private final int rows, cols;

    public CinemaHall(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        seats = new Seat[rows][cols];
        initializeSeats();
    }

    private void initializeSeats() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                seats[i][j] = new Seat(false, "%c%d".formatted('A' + i, j + 1), null);
            }
        }
    }

    public boolean bookSeat(int row, int col, Customer customer) {
        if (!seats[row][col].booked()) {
            seats[row][col] = seats[row][col].book(customer);
            return true;
        }
        return false;
    }

    public boolean bookAdjacentSeats(int row, int startCol, int count, Customer customer) {
        if (startCol + count > cols) return false;
        for (int i = 0; i < count; i++) {
            if (seats[row][startCol + i].booked()) return false;
        }
        for (int i = 0; i < count; i++) {
            seats[row][startCol + i] = seats[row][startCol + i].book(customer);
        }
        return true;
    }


    public Seat[][] getSeats() {
        return seats;
    }

    public void listBookedSeats() {
        System.out.println("Booked Seats:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (seats[i][j].booked()) {
                    System.out.println(seats[i][j].label() + " - " + seats[i][j].bookedBy().name() + " - " + seats[i][j].bookedBy().email());
                }
            }
        }
    }
}
