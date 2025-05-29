package exceptions;

public class LocatieNotFoundException extends RuntimeException {
    public LocatieNotFoundException(long id) {
        super("Locatie with ID " + id + " not found.");
    }
}
