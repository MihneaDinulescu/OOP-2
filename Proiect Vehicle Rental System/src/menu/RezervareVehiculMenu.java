package menu;

import models.RezervareVehicul;
import service.RezervareVehiculService;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RezervareVehiculMenu {
    private final RezervareVehiculService rezervareVehiculService;
    private final Scanner scanner;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public RezervareVehiculMenu(Connection connection, Scanner scanner) {
        this.rezervareVehiculService = RezervareVehiculService.getInstance(connection);
        this.scanner = scanner;
    }

    public void showMenu() {
        int option = -1;
        while (option != 0) {
            System.out.println("\n--- Meniu RezervareVehicul ---");
            System.out.println("1. Adaugă rezervare vehicul");
            System.out.println("2. Afișează toate rezervările vehicul");
            System.out.println("3. Caută rezervare vehicul după ID");
            System.out.println("4. Actualizează rezervare vehicul");
            System.out.println("5. Șterge rezervare vehicul");
            System.out.println("0. Înapoi");
            System.out.print("Alege o opțiune: ");

            try {
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1 -> addRezervareVehicul();
                    case 2 -> afiseazaToateRezervarileVehicul();
                    case 3 -> cautaRezervareVehiculById();
                    case 4 -> updateRezervareVehicul();
                    case 5 -> deleteRezervareVehicul();
                    case 0 -> System.out.println("Înapoi la meniul principal...");
                    default -> System.out.println("Opțiune invalidă!");
                }
            } catch (NumberFormatException | SQLException | ParseException e) {
                System.out.println("Eroare: " + e.getMessage());
            }
        }
    }

    private void addRezervareVehicul() throws SQLException, ParseException {
        System.out.print("ID Rezervare: ");
        long idRezervare = Long.parseLong(scanner.nextLine());

        System.out.print("ID Vehicul: ");
        long idVehicul = Long.parseLong(scanner.nextLine());

        System.out.print("Data început (yyyy-MM-dd): ");
        Date startDate = dateFormat.parse(scanner.nextLine());

        System.out.print("Data sfârșit (yyyy-MM-dd): ");
        Date endDate = dateFormat.parse(scanner.nextLine());

        RezervareVehicul rv = new RezervareVehicul(0, null, null, idRezervare, idVehicul, startDate, endDate);
        rezervareVehiculService.addRezervareVehicul(rv);

        System.out.println("Rezervare vehicul adăugată cu succes!");
    }

    private void afiseazaToateRezervarileVehicul() throws SQLException {
        List<RezervareVehicul> rezervariVehicul = rezervareVehiculService.getAllRezervariVehicul();
        if (rezervariVehicul.isEmpty()) {
            System.out.println("Nu există rezervări vehicul.");
        } else {
            for (RezervareVehicul rv : rezervariVehicul) {
                System.out.println("ID RezervareVehicul: " + rv.getIdRezervareVehicul() +
                        ", ID Rezervare: " + rv.getIdRezervare() +
                        ", ID Vehicul: " + rv.getIdVehicul() +
                        ", Data început: " + dateFormat.format(rv.getStartDate()) +
                        ", Data sfârșit: " + dateFormat.format(rv.getEndDate()));
            }
        }
    }

    private void cautaRezervareVehiculById() throws SQLException {
        System.out.print("Introdu ID rezervare vehicul: ");
        long id = Long.parseLong(scanner.nextLine());

        RezervareVehicul rv = rezervareVehiculService.getRezervareVehiculById(id);
        if (rv != null) {
            System.out.println("ID RezervareVehicul: " + rv.getIdRezervareVehicul() +
                    ", ID Rezervare: " + rv.getIdRezervare() +
                    ", ID Vehicul: " + rv.getIdVehicul() +
                    ", Data început: " + dateFormat.format(rv.getStartDate()) +
                    ", Data sfârșit: " + dateFormat.format(rv.getEndDate()));
        } else {
            System.out.println("Rezervarea vehicul nu a fost găsită.");
        }
    }

    private void updateRezervareVehicul() throws SQLException, ParseException {
        System.out.print("Introdu ID rezervare vehicul pentru actualizare: ");
        long id = Long.parseLong(scanner.nextLine());

        RezervareVehicul rv = rezervareVehiculService.getRezervareVehiculById(id);
        if (rv == null) {
            System.out.println("Rezervarea vehicul nu a fost găsită.");
            return;
        }

        System.out.print("Noul ID Rezervare (curent: " + rv.getIdRezervare() + "): ");
        long idRezervare = Long.parseLong(scanner.nextLine());

        System.out.print("Noul ID Vehicul (curent: " + rv.getIdVehicul() + "): ");
        long idVehicul = Long.parseLong(scanner.nextLine());

        System.out.print("Noua dată început (yyyy-MM-dd) (curent: " + dateFormat.format(rv.getStartDate()) + "): ");
        Date startDate = dateFormat.parse(scanner.nextLine());

        System.out.print("Noua dată sfârșit (yyyy-MM-dd) (curent: " + dateFormat.format(rv.getEndDate()) + "): ");
        Date endDate = dateFormat.parse(scanner.nextLine());

        rv.setIdRezervare(idRezervare);
        rv.setIdVehicul(idVehicul);
        rv.setStartDate(startDate);
        rv.setEndDate(endDate);

        rezervareVehiculService.updateRezervareVehicul(rv);
        System.out.println("Rezervare vehicul actualizată cu succes!");
    }

    private void deleteRezervareVehicul() throws SQLException {
        System.out.print("Introdu ID rezervare vehicul pentru ștergere: ");
        long id = Long.parseLong(scanner.nextLine());

        rezervareVehiculService.deleteRezervareVehicul(id);
        System.out.println("Rezervare vehicul ștearsă cu succes!");
    }
}
