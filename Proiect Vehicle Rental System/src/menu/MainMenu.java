package menu;

import config.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import menu.*;

public class MainMenu {

    public static void main(String[] args) {
        try (
                Connection connection = ConnectionProvider.getConnection();
                Scanner scanner = new Scanner(System.in)
        ) {
            boolean running = true;

            while (running) {
                System.out.println("=== VEHICLE RENTAL SYSTEM ===");
                System.out.println("1. Meniu Angajat");
                System.out.println("2. Meniu Client");
                System.out.println("3. Meniu Locatie");
                System.out.println("4. Meniu Recenzie");
                System.out.println("5. Meniu Rezervare");
                System.out.println("6. Meniu Rezervare Vehicul");
                System.out.println("7. Meniu Vehicul");
                System.out.println("8. Meniu Functionalitati");
                System.out.println("0. Iesire");
                System.out.print("Alege o optiune: ");

                String opt = scanner.nextLine();

                try {
                    switch (opt) {
                        case "1" -> new AngajatMenu(connection, scanner).showMenu();
                        case "2" -> new ClientMenu(connection, scanner).afiseazaMeniu();
                        case "3" -> new LocatieMenu(
                                service.LocatieService.getInstance(connection), scanner
                        ).showMenu();
                        case "4" -> new RecenzieMenu(connection, scanner).afiseazaMeniu();
                        case "5" -> new RezervareMenu(connection, scanner).showMenu();
                        case "6" -> new RezervareVehiculMenu(connection, scanner).showMenu();
                        case "7" -> new VehiculMenu(connection, scanner).showMenu();
                        case "8" -> new FunctionalitatiMenu(connection, scanner).start();
                        case "0" -> {
                            System.out.println("La revedere!");
                            running = false;
                        }
                        default -> System.out.println("Optiune invalida. Incearca din nou.");
                    }
                } catch (SQLException e) {
                    System.err.println("Eroare SQL: " + e.getMessage());
                }

                System.out.println();
            }

        } catch (SQLException e) {
            System.err.println("Nu s-a putut stabili conexiunea: " + e.getMessage());
        }
    }
}
