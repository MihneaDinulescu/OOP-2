package src.main.java.lab5;

class InvalidDataFormatException extends RuntimeException {
    public InvalidDataFormatException(String line) {
        super("Malformed line: " + line);
    }
}