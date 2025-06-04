package menu;

import models.Rezervare;
import service.RezervareService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class RezervareMenu {
    private final RezervareService rezervareService;
    private final Scanner scanner;

    public RezervareMenu(Connection connection, Scanner scanner) {
        this.rezervareService = RezervareService.getInstance(connection);
        this.scanner = scanner;
    }

    public void showMenu() {
        int option = -1;
        while (option != 0) {
            System.out.println("\n--- Meniu Rezervare ---");
            System.out.println("1. Adaugă rezervare");
            System.out.println("2. Afișează toate rezervările");
            System.out.println("3. Caută rezervare după ID");
            System.out.println("4. Actualizează rezervare");
            System.out.println("5. Șterge rezervare");
            System.out.println("0. Înapoi");
            System.out.print("Alege o opțiune: ");

            try {
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1 -> addRezervare();
                    case 2 -> afiseazaToateRezervarile();
                    case 3 -> cautaRezervareById();
                    case 4 -> updateRezervare();
                    case 5 -> deleteRezervare();
                    case 0 -> System.out.println("Înapoi la meniul principal...");
                    default -> System.out.println("Opțiune invalidă!");
                }
            } catch (NumberFormatException | SQLException e) {
                System.out.println("Eroare: " + e.getMessage());
            }
        }
    }

    private void addRezervare() throws SQLException {
        System.out.print("ID Client: ");
        long idClient = Long.parseLong(scanner.nextLine());

        System.out.print("Avans: ");
        double avans = Double.parseDouble(scanner.nextLine());

        System.out.print("Metodă plată: ");
        String metodaPlata = scanner.nextLine();

        Rezervare rezervare = new Rezervare(0, null, idClient, avans, metodaPlata);
        rezervareService.addRezervare(rezervare);

        System.out.println("Rezervare adăugată cu succes!");
    }

    private void afiseazaToateRezervarile() throws SQLException {
        List<Rezervare> rezervari = rezervareService.getAllRezervari();
        if (rezervari.isEmpty()) {
            System.out.println("Nu există rezervări.");
        } else {
            for (Rezervare r : rezervari) {
                System.out.println("ID: " + r.getIdRezervare() +
                        ", ID Client: " + r.getIdClient() +
                        ", Avans: " + r.getAvans() +
                        ", Metodă plată: " + r.getMetodaPlata());
            }
        }
    }

    private void cautaRezervareById() throws SQLException {
        System.out.print("Introdu ID rezervare: ");
        long id = Long.parseLong(scanner.nextLine());
        Rezervare r = rezervareService.getRezervareById(id);
        if (r != null) {
            System.out.println("ID: " + r.getIdRezervare() +
                    ", ID Client: " + r.getIdClient() +
                    ", Avans: " + r.getAvans() +
                    ", Metodă plată: " + r.getMetodaPlata());
        } else {
            System.out.println("Rezervarea nu a fost găsită.");
        }
    }

    private void updateRezervare() throws SQLException {
        System.out.print("Introdu ID rezervare pentru actualizare: ");
        long id = Long.parseLong(scanner.nextLine());

        Rezervare r = rezervareService.getRezervareById(id);
        if (r == null) {
            System.out.println("Rezervarea nu a fost găsită.");
            return;
        }

        System.out.print("Nou ID Client (curent: " + r.getIdClient() + "): ");
        long idClient = Long.parseLong(scanner.nextLine());

        System.out.print("Noul avans (curent: " + r.getAvans() + "): ");
        double avans = Double.parseDouble(scanner.nextLine());

        System.out.print("Noua metodă plată (curent: " + r.getMetodaPlata() + "): ");
        String metodaPlata = scanner.nextLine();

        r.setIdClient(idClient);
        r.setAvans(avans);
        r.setMetodaPlata(metodaPlata);

        rezervareService.updateRezervare(r);
        System.out.println("Rezervare actualizată cu succes!");
    }

    private void deleteRezervare() throws SQLException {
        System.out.print("Introdu ID rezervare pentru ștergere: ");
        long id = Long.parseLong(scanner.nextLine());

        rezervareService.deleteRezervare(id);
        System.out.println("Rezervare ștearsă cu succes!");
    }
}
