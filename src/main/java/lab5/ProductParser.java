package src.main.java.lab5;

class ProductParser {
    public static Product parseProduct(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            throw new InvalidDataFormatException(line);
        }

        String productId = parts[0].trim();
        String name = parts[1].trim();
        double price;

        try {
            price = Double.parseDouble(parts[2].trim());
        } catch (NumberFormatException e) {
            throw new InvalidDataFormatException(line);
        }

        if (price < 0) {
            throw new InvalidPriceException(productId);
        }

        return new Product(productId, name, price);
    }
}