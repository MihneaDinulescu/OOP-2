package src.main.java.lab5;

class InvalidPriceException extends RuntimeException {
    public InvalidPriceException(String productId) {
        super("Negative price for product with ID: " + productId);
    }
}