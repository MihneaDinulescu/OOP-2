package src.main.java.lab5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProductInventoryApp {
    public static void main(String[] args) {
        String fileName = "C:\\Users\\dinul\\OneDrive\\Desktop\\fisObiect.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    Product product = ProductParser.parseProduct(line);
                    System.out.println(product);
                } catch (InvalidDataFormatException | InvalidPriceException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}