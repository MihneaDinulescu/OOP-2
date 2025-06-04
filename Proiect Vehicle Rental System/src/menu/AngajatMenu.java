package menu;

import models.Angajat;
import service.AngajatService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AngajatMenu {
    private final AngajatService angajatService;
    private final Scanner scanner;

    public AngajatMenu(Connection connection, Scanner scanner) {
        this.angajatService = AngajatService.getInstance(connection);
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n-- Meniu Angajat ---");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Afișează angajat după ID");
            System.out.println("3. Afișează toți angajații");
            System.out.println("4. Actualizează angajat");
            System.out.println("5. Șterge angajat");
            System.out.println("0. Înapoi la meniul principal");
            System.out.print("Alege opțiunea: ");

            String opt = scanner.nextLine();

            try {
                switch (opt) {
                    case "1" -> adaugaAngajat();
                    case "2" -> afiseazaAngajatById();
                    case "3" -> afiseazaTotiAngajatii();
                    case "4" -> actualizeazaAngajat();
                    case "5" -> stergeAngajat();
                    case "0" -> {
                        return;
                    }
                    default -> System.out.println("Opțiune invalidă!");
                }
            } catch (SQLException e) {
                System.out.println("Eroare SQL: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Input invalid, te rog introdu un număr valid.");
            }
        }
    }

    private void adaugaAngajat() throws SQLException {
        System.out.println("=== Adaugă angajat nou ===");
        System.out.print("Nume angajat: ");
        String nume = scanner.nextLine();

        System.out.print("Prenume angajat: ");
        String prenume = scanner.nextLine();

        System.out.print("Număr telefon: ");
        String nrTelefon = scanner.nextLine();

        System.out.print("Email angajat: ");
        String email = scanner.nextLine();

        System.out.print("Funcție: ");
        String functie = scanner.nextLine();

        System.out.print("ID locație: ");
        long idLocatie = Long.parseLong(scanner.nextLine());

        Angajat angajat = new Angajat(0, nume, prenume, nrTelefon, email, functie, null, idLocatie);
        angajatService.addAngajat(angajat);
        System.out.println("Angajat adăugat cu succes!");
    }

    private void afiseazaAngajatById() throws SQLException {
        System.out.print("Introdu ID angajat: ");
        long id = Long.parseLong(scanner.nextLine());

        Angajat angajat = angajatService.getAngajatByIdWithLocatieAndVehicule(id);

        if (angajat == null) {
            System.out.println("Nu există angajat cu ID-ul " + id);
            return;
        }

        afiseazaDetaliiAngajat(angajat);
    }

    private void afiseazaTotiAngajatii() throws SQLException {
        List<Angajat> lista = angajatService.getAllAngajati();
        if (lista.isEmpty()) {
            System.out.println("Nu există angajați înregistrati.");
            return;
        }
        System.out.println("Lista angajaților:");
        for (Angajat a : lista) {
            System.out.printf("%d - %s %s | Tel: %s | Email: %s | Funcție: %s | ID locație: %d%n",
                    a.getIdAngajat(), a.getNumeAngajat(), a.getPrenumeAngajat(),
                    a.getNrTelefon(), a.getEmailAngajat(), a.getFunctie(), a.getIdLocatie());
        }
    }

    private void actualizeazaAngajat() throws SQLException {
        System.out.print("Introdu ID angajat de actualizat: ");
        long id = Long.parseLong(scanner.nextLine());

        Angajat angajat = angajatService.getAngajatById(id);
        if (angajat == null) {
            System.out.println("Nu există angajat cu ID-ul " + id);
            return;
        }

        System.out.println("Completează doar câmpurile pe care vrei să le modifici (lasă gol pentru a păstra valoarea curentă):");

        System.out.print("Nume [" + angajat.getNumeAngajat() + "]: ");
        String nume = scanner.nextLine();
        if (!nume.isBlank()) angajat.setNumeAngajat(nume);

        System.out.print("Prenume [" + angajat.getPrenumeAngajat() + "]: ");
        String prenume = scanner.nextLine();
        if (!prenume.isBlank()) angajat.setPrenumeAngajat(prenume);

        System.out.print("Nr telefon [" + angajat.getNrTelefon() + "]: ");
        String telefon = scanner.nextLine();
        if (!telefon.isBlank()) angajat.setNrTelefon(telefon);

        System.out.print("Email [" + angajat.getEmailAngajat() + "]: ");
        String email = scanner.nextLine();
        if (!email.isBlank()) angajat.setEmailAngajat(email);

        System.out.print("Funcție [" + angajat.getFunctie() + "]: ");
        String functie = scanner.nextLine();
        if (!functie.isBlank()) angajat.setFunctie(functie);

        System.out.print("ID locație [" + angajat.getIdLocatie() + "]: ");
        String idLoc = scanner.nextLine();
        if (!idLoc.isBlank()) angajat.setIdLocatie(Long.parseLong(idLoc));

        angajatService.updateAngajat(angajat);
        System.out.println("Angajat actualizat cu succes!");
    }

    private void stergeAngajat() throws SQLException {
        System.out.print("Introdu ID angajat de șters: ");
        long id = Long.parseLong(scanner.nextLine());

        Angajat angajat = angajatService.getAngajatById(id);
        if (angajat == null) {
            System.out.println("Nu există angajat cu ID-ul " + id);
            return;
        }

        angajatService.deleteAngajat(id);
        System.out.println("Angajat șters cu succes!");
    }

    private void afiseazaDetaliiAngajat(Angajat angajat) {
        System.out.println("\n--- Detalii angajat ---");
        System.out.println("ID: " + angajat.getIdAngajat());
        System.out.println("Nume: " + angajat.getNumeAngajat());
        System.out.println("Prenume: " + angajat.getPrenumeAngajat());
        System.out.println("Telefon: " + angajat.getNrTelefon());
        System.out.println("Email: " + angajat.getEmailAngajat());
        System.out.println("Funcție: " + angajat.getFunctie());

        if (angajat.getLocatie() != null) {
            System.out.println("Locație: " + angajat.getLocatie().getOras() + ", " + angajat.getLocatie().getTara());
        } else {
            System.out.println("Locație: N/A");
        }

        if (angajat.getVehicule() != null && !angajat.getVehicule().isEmpty()) {
            System.out.println("Vehicule asociate:");
            angajat.getVehicule().forEach(v -> System.out.println("  - " + v.getModel() + " (" + v.getNumarInmatriculare() + ")"));
        } else {
            System.out.println("Vehicule asociate: Niciunul");
        }
    }
}
