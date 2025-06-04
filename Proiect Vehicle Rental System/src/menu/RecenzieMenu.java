package menu;

import models.Recenzie;
import service.RecenzieService;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RecenzieMenu {
    private final RecenzieService recenzieService;
    private final Scanner scanner;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public RecenzieMenu(Connection connection, Scanner scanner) {
        this.recenzieService = RecenzieService.getInstance(connection);
        this.scanner = scanner;
    }

    public void afiseazaMeniu() throws SQLException {
        int optiune;
        do {
            System.out.println("\n--- Meniu Recenzie ---");
            System.out.println("1. Adaugă recenzie");
            System.out.println("2. Afișează toate recenziile");
            System.out.println("3. Caută recenzie după ID");
            System.out.println("4. Actualizează recenzie");
            System.out.println("5. Șterge recenzie");
            System.out.println("0. Înapoi la meniul principal");
            System.out.print("Alege o opțiune: ");

            optiune = Integer.parseInt(scanner.nextLine());

            switch (optiune) {
                case 1 -> adaugaRecenzie();
                case 2 -> afiseazaToateRecenziile();
                case 3 -> cautaRecenzieDupaId();
                case 4 -> actualizeazaRecenzie();
                case 5 -> stergeRecenzie();
                case 0 -> System.out.println("Înapoi la meniul principal...");
                default -> System.out.println("Opțiune invalidă!");
            }
        } while (optiune != 0);
    }

    private void adaugaRecenzie() throws SQLException {
        System.out.println("Introdu datele pentru noua recenzie:");

        System.out.print("Număr stele (1-5): ");
        int nrStele;
        try {
            nrStele = Integer.parseInt(scanner.nextLine());
            if (nrStele < 1 || nrStele > 5) {
                System.out.println("Numărul de stele trebuie să fie între 1 și 5!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Număr stele invalid!");
            return;
        }

        System.out.print("Data recenziei (yyyy-MM-dd): ");
        Date dataRecenzie;
        try {
            dataRecenzie = sdf.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Format dată invalid!");
            return;
        }

        Recenzie recenzie = new Recenzie(0, nrStele, dataRecenzie, null);
        recenzieService.addRecenzie(recenzie);
        System.out.println("Recenzie adăugată cu succes!");
    }

    private void afiseazaToateRecenziile() throws SQLException {
        List<Recenzie> recenzii = recenzieService.getAllRecenzii();
        if (recenzii.isEmpty()) {
            System.out.println("Nu există recenzii.");
        } else {
            recenzii.forEach(r -> System.out.println(
                    "ID: " + r.getIdRecenzie() +
                            ", Stele: " + r.getNrStele() +
                            ", Dată: " + sdf.format(r.getDataRecenzie())
            ));
        }
    }

    private void cautaRecenzieDupaId() throws SQLException {
        System.out.print("Introdu ID recenzie: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            Recenzie recenzie = recenzieService.getRecenzieById(id);
            if (recenzie != null) {
                System.out.println("ID: " + recenzie.getIdRecenzie() +
                        ", Stele: " + recenzie.getNrStele() +
                        ", Dată: " + sdf.format(recenzie.getDataRecenzie()));
            } else {
                System.out.println("Recenzia nu a fost găsită.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID invalid.");
        }
    }

    private void actualizeazaRecenzie() throws SQLException {
        System.out.print("Introdu ID-ul recenziei de actualizat: ");
        long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID invalid.");
            return;
        }

        Recenzie recenzieExistenta = recenzieService.getRecenzieById(id);
        if (recenzieExistenta == null) {
            System.out.println("Recenzia nu a fost găsită.");
            return;
        }

        System.out.print("Număr stele nou (1-5): ");
        int nrStele;
        try {
            nrStele = Integer.parseInt(scanner.nextLine());
            if (nrStele < 1 || nrStele > 5) {
                System.out.println("Număr invalid.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Număr invalid.");
            return;
        }

        System.out.print("Data nouă a recenziei (yyyy-MM-dd): ");
        Date dataNoua;
        try {
            dataNoua = sdf.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Dată invalidă.");
            return;
        }

        Recenzie recenzieNoua = new Recenzie(id, nrStele, dataNoua, recenzieExistenta.getRezervare());
        recenzieService.updateRecenzie(recenzieNoua);
        System.out.println("Recenzie actualizată cu succes!");
    }

    private void stergeRecenzie() throws SQLException {
        System.out.print("Introdu ID-ul recenziei de șters: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            recenzieService.deleteRecenzie(id);
            System.out.println("Recenzie ștearsă cu succes!");
        } catch (NumberFormatException e) {
            System.out.println("ID invalid.");
        }
    }
}
