package menu;

import models.Locatie;
import service.LocatieService;
import exceptions.LocatieNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class LocatieMenu {
    private final LocatieService locatieService;
    private final Scanner scanner;

    public LocatieMenu(LocatieService locatieService, Scanner scanner) {
        this.locatieService = locatieService;
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Meniu Locație ---");
            System.out.println("1. Adaugă locație nouă");
            System.out.println("2. Afișează locație după ID");
            System.out.println("3. Afișează toate locațiile");
            System.out.println("4. Actualizează locație");
            System.out.println("5. Șterge locație");
            System.out.println("0. Înapoi");
            System.out.print("Alege o opțiune: ");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Te rog să introduci un număr valid!");
                continue;
            }

            try {
                switch (option) {
                    case 1 -> adaugaLocatie();
                    case 2 -> afiseazaLocatieDupaId();
                    case 3 -> afiseazaToateLocatiile();
                    case 4 -> actualizeazaLocatie();
                    case 5 -> stergeLocatie();
                    case 0 -> {
                        System.out.println("Ieșire din meniul locații.");
                        return;
                    }
                    default -> System.out.println("Opțiune invalidă, încearcă din nou.");
                }
            } catch (SQLException e) {
                System.out.println("Eroare SQL: " + e.getMessage());
            } catch (LocatieNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void adaugaLocatie() throws SQLException {
        System.out.println("Introdu datele pentru noua locație:");

        System.out.print("Țară: ");
        String tara = scanner.nextLine();

        System.out.print("Oraș: ");
        String oras = scanner.nextLine();

        System.out.print("Stradă: ");
        String strada = scanner.nextLine();

        System.out.print("Număr stradă: ");
        Integer nrStrada = null;
        try {
            String nrStradaStr = scanner.nextLine();
            if (!nrStradaStr.isBlank()) {
                nrStrada = Integer.parseInt(nrStradaStr);
            }
        } catch (NumberFormatException e) {
            System.out.println("Numărul străzii trebuie să fie un număr valid!");
            return;
        }

        Locatie locatie = new Locatie(7, tara, oras, strada, nrStrada);
        locatieService.addLocatie(locatie);
        System.out.println("Locație adăugată cu succes!");
    }

    private void afiseazaLocatieDupaId() throws SQLException, LocatieNotFoundException {
        System.out.print("Introdu ID locație: ");
        long id = Long.parseLong(scanner.nextLine());

        Locatie locatie = locatieService.getLocatieById(id);
        afiseazaLocatie(locatie);
    }

    private void afiseazaToateLocatiile() throws SQLException {
        List<Locatie> locatii = locatieService.getAllLocatii();
        if (locatii.isEmpty()) {
            System.out.println("Nu există locații înregistrate.");
            return;
        }
        System.out.println("Lista locațiilor:");
        for (Locatie l : locatii) {
            afiseazaLocatie(l);
        }
    }

    private void actualizeazaLocatie() throws SQLException, LocatieNotFoundException {
        System.out.print("Introdu ID locație de actualizat: ");
        long id = Long.parseLong(scanner.nextLine());

        Locatie locatie = locatieService.getLocatieById(id);

        System.out.println("Date curente:");
        afiseazaLocatie(locatie);

        System.out.print("Țară nouă (lasă gol pentru a păstra): ");
        String taraNoua = scanner.nextLine();
        if (!taraNoua.isBlank()) {
            locatie.setTara(taraNoua);
        }

        System.out.print("Oraș nou (lasă gol pentru a păstra): ");
        String orasNou = scanner.nextLine();
        if (!orasNou.isBlank()) {
            locatie.setOras(orasNou);
        }

        System.out.print("Stradă nouă (lasă gol pentru a păstra): ");
        String stradaNoua = scanner.nextLine();
        if (!stradaNoua.isBlank()) {
            locatie.setStrada(stradaNoua);
        }

        System.out.print("Număr stradă nou (lasă gol pentru a păstra): ");
        String nrStradaNoua = scanner.nextLine();
        if (!nrStradaNoua.isBlank()) {
            try {
                locatie.setNrStrada(Integer.parseInt(nrStradaNoua));
            } catch (NumberFormatException e) {
                System.out.println("Numărul străzii trebuie să fie un număr valid!");
                return;
            }
        }

        locatieService.updateLocatie(locatie);
        System.out.println("Locația a fost actualizată cu succes!");
    }

    private void stergeLocatie() throws SQLException {
        System.out.print("Introdu ID locație de șters: ");
        long id = Long.parseLong(scanner.nextLine());
        locatieService.deleteLocatie(id);
        System.out.println("Locația a fost ștearsă cu succes!");
    }

    private void afiseazaLocatie(Locatie locatie) {
        System.out.println("ID: " + locatie.getIdLocatie()
                + ", Țară: " + locatie.getTara()
                + ", Oraș: " + locatie.getOras()
                + ", Stradă: " + locatie.getStrada()
                + ", Nr. stradă: " + locatie.getNrStrada());
    }
}
