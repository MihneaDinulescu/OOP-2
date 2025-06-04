package menu;

import models.*;
import service.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FunctionalitatiMenu {
    private final AngajatService angajatService;
    private final LocatieService locatieService;
    private final ClientService clientService;
    private final VehiculService vehiculService;
    private final RezervareService rezervareService;
    private final RezervareVehiculService rezervareVehiculService;
    private final RecenzieService recenzieService;
    private final AuditService audit;
    private final Scanner scanner;

    public FunctionalitatiMenu(Connection connection, Scanner scanner) {
        this.angajatService = AngajatService.getInstance(connection);
        this.locatieService = LocatieService.getInstance(connection);
        this.clientService = ClientService.getInstance(connection);
        this.vehiculService = VehiculService.getInstance(connection);
        this.rezervareService = RezervareService.getInstance(connection);
        this.rezervareVehiculService = RezervareVehiculService.getInstance(connection);
        this.recenzieService = RecenzieService.getInstance(connection);
        this.audit = AuditService.getInstance();
        this.scanner = scanner;
    }

    public void start() throws SQLException {
        int option = -1;
        while (option != 0) {
            System.out.println("\n--- Meniu Funcționalități ---");
            System.out.println("1. Top 3 Clienți cu Cele Mai Multe Rezervări");
            System.out.println("2. Vehicule Disponibile într-o Perioadă");
            System.out.println("3. Angajatul cu Cele Mai Multe Vehicule");
            System.out.println("4. Clienți și Număr Total Rezervări");
            System.out.println("5. Media Recenziilor pentru un Vehicul");
            System.out.println("6. Angajați și Locații cu Număr Vehicule");
            System.out.println("7. Top 5 Vehicule după Rating și Minim 5 Recenzii");
            System.out.println("8. Clienți cu Avans Maxim");
            System.out.println("9. Total Plata pentru o Rezervare");
            System.out.println("10. Locații cu Număr Rezervări");
            System.out.println("11. Vehicule fără Rezervări");
            System.out.println("12. Număr Rezervări pe Lunile Anului");
            System.out.println("13. Distribuția Vehiculelor pe Locații");
            System.out.println("14. Recenzii Negative");
            System.out.println("15. Locații Fără Angajați");
            System.out.println("0. Ieșire");
            System.out.print("Alege o opțiune: ");

            try {
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1 -> top3ClientiCuCeleMaiMulteRezervari();
                    case 2 -> vehiculeDisponibileIntrOPerioada();
                    case 3 -> angajatCuCeleMaiMulteVehicule();
                    case 4 -> clientiCuNumarTotalRezervari();
                    case 5 -> mediaRecenziiPentruVehicul();
                    case 6 -> angajatiLocatiiNumarVehicule();
                    case 7 -> top5VehiculeDupaRating();
                    case 8 -> clientiCuAvansMaxim();
                    case 9 -> totalPlataPentruRezervare();
                    case 10 -> locatiiCuNumarRezervari();
                    case 11 -> vehiculeFaraRezervari();
                    case 12 -> numarRezervariLunare();
                    case 13 -> distributieVehiculePeLocatii();
                    case 14 -> recenziiNegative();
                    case 15 -> locatiiFaraAngajati();
                    case 0 -> System.out.println("La revedere!");
                    default -> System.out.println("Opțiune invalidă!");
                }
            } catch (NumberFormatException | SQLException e) {
                System.out.println("Eroare: " + e.getMessage());
            }
        }
    }

    private void top3ClientiCuCeleMaiMulteRezervari() throws SQLException {
        System.out.println("Top 3 Clienți cu Cele Mai Multe Rezervări:");
        List<Client> topClienti = clientService.getTop3ClientiCuCeleMaiMulteRezervari();
        for (Client c : topClienti) {
            System.out.println("- " + c.getNume() + " " + c.getPrenume() + " | Telefon: " + c.getNumarTelefon());
        }
        audit.log("Top 3 Clienți cu Cele Mai Multe Rezervări");
    }

    private void vehiculeDisponibileIntrOPerioada() throws SQLException {
        System.out.print("Data start (yyyy-mm-dd): ");
        Date startDate = Date.valueOf(scanner.nextLine());
        System.out.print("Data end (yyyy-mm-dd): ");
        Date endDate = Date.valueOf(scanner.nextLine());
        List<Vehicul> vehiculeDisponibile = vehiculService.getVehiculeDisponibile(startDate, endDate);
        System.out.println("\nVehicule Disponibile între " + startDate + " și " + endDate + ":");
        vehiculeDisponibile.forEach(v -> System.out.println(" - " + v.getModel() + " (" + v.getNumarInmatriculare() + ")"));
        audit.log("Vehicule Disponibile în Perioada " + startDate + " - " + endDate);
    }

    private void angajatCuCeleMaiMulteVehicule() throws SQLException {
        Angajat topAngajat = angajatService.findAngajatCuCeleMaiMulteVehicule();
        if (topAngajat != null) {
            System.out.println("\nAngajatul cu Cele Mai Multe Vehicule:");
            System.out.println(topAngajat.getNumeAngajat() + " " + topAngajat.getPrenumeAngajat());
        }
        audit.log("Angajatul cu Cele Mai Multe Vehicule");
    }

    private void clientiCuNumarTotalRezervari() throws SQLException {
        System.out.println("\nClienți și Numărul Total de Rezervări:");
        Map<Client, Integer> clientiCuNrRezervari = clientService.getClientiCuNumarRezervari();
        clientiCuNrRezervari.forEach((client, nr) ->
                System.out.println(client.getNume() + " " + client.getPrenume() + " - " + nr + " rezervări"));
        audit.log("Mapare Client cu Număr Total de Rezervări");
    }

    private void mediaRecenziiPentruVehicul() throws SQLException {
        System.out.print("Introdu ID vehicul: ");
        long idVehicul = Long.parseLong(scanner.nextLine());
        Double mediaRecenzii = vehiculService.getMediaRecenziiPentruVehicul(idVehicul);
        System.out.println("\nMedia Recenziilor pentru Vehiculul cu ID " + idVehicul + ": " +
                (mediaRecenzii != null ? mediaRecenzii : "Nu există recenzii"));
        audit.log("Media Recenzii per Vehicul");
    }

    private void angajatiLocatiiNumarVehicule() throws SQLException {
        System.out.println("\nAngajați și Locațiile Lor cu Numărul Total de Vehicule Gestionate:");
        Map<Angajat, Integer> angajatiCuVehicule = angajatService.findAllWithNumarVehicule();
        angajatiCuVehicule.forEach((a, nr) ->
                System.out.println(a.getNumeAngajat() + " - " + a.getLocatie().getOras() + ": " + nr + " vehicule"));
        audit.log("Angajați și Locațiile Lor cu Numărul Total de Vehicule");
    }

    private void top5VehiculeDupaRating() throws SQLException {
        System.out.println("\nTop 5 Vehicule cu Cea Mai Mare Medie a Ratingului (Minim 5 Recenzii):");
        Map<Vehicul, Double> top5 = vehiculService.getTop5VehiculeCuCeaMaiMareMedieRatingMinim5Recenzii();
        int i = 1;
        for (Map.Entry<Vehicul, Double> entry : top5.entrySet()) {
            System.out.printf("%d. %s (%s) - %.2f%n", i++, entry.getKey().getModel(), entry.getKey().getNumarInmatriculare(), entry.getValue());
        }
        audit.log("Top 5 Vehicule cu Cea Mai Mare Medie a Ratingului și Minim 5 Recenzii");
    }

    private void clientiCuAvansMaxim() throws SQLException {
        System.out.println("\nClienți și Avansul Maxim Plătit într-o Rezervare:");
        clientService.getClientsWithMaxAvansMap().forEach((client, avans) ->
                System.out.println(client.getNume() + " " + client.getPrenume() + " - Avans maxim: " + avans));
        audit.log("Afișare Clienți cu Avans Maxim");
    }

    private void totalPlataPentruRezervare() throws SQLException {
        System.out.print("Introdu ID rezervare: ");
        long idRezervare = Long.parseLong(scanner.nextLine());
        double totalPlata = rezervareService.calculeazaTotalPlataPerRezervare(idRezervare);
        System.out.println("\nTotal de Plată pentru Rezervarea cu ID " + idRezervare + ": " + totalPlata);
        audit.log("Calculul Totalului de Plată pe Rezervare");
    }

    private void locatiiCuNumarRezervari() throws SQLException {
        System.out.println("\nLocații și Numărul de Rezervări:");
        locatieService.getLocatiiCuNumarRezervari().forEach((loc, nr) ->
                System.out.println(loc.getOras() + ", " + loc.getTara() + " - " + nr + " rezervări"));
        audit.log("Locațiile cu Numărul Lor de Rezervări");
    }

    private void vehiculeFaraRezervari() throws SQLException {
        System.out.println("\nVehicule care Nu au Fost Rezervate Niciodată:");
        List<Vehicul> faraRezervari = vehiculService.getVehiculeFaraRezervari();
        faraRezervari.forEach(v ->
                System.out.println(v.getModel() + " (" + v.getNumarInmatriculare() + ")"));
        audit.log("Vehicule care Nu au Fost Rezervate Niciodată");
    }

    private void numarRezervariLunare() throws SQLException {
        System.out.println("\nNumăr de Rezervări pe Lunile Anului:");
        rezervareService.getNumarRezervariLunare().forEach((luna, nr) ->
                System.out.println("Luna: " + luna + " - " + nr + " rezervări"));
        audit.log("Număr de Rezervări pe Lunile Anului");
    }

    private void distributieVehiculePeLocatii() throws SQLException {
        System.out.println("\nDistribuția Vehiculelor pe Locații:");
        vehiculService.getDistributieVehiculePeLocatii().forEach((loc, nr) ->
                System.out.println("Locație: " + loc + " - " + nr + " vehicule"));
        audit.log("Distribuția Vehiculelor per Locație");
    }

    private void recenziiNegative() throws SQLException {
        System.out.println("\nRecenzii Negative:");
        recenzieService.getRecenziiNegative().forEach(r ->
                System.out.println("ID: " + r.getIdRecenzie() + " - Stele: " + r.getNrStele() + " - Data: " + r.getDataRecenzie()));
        audit.log("Obținerea Recenziilor Negative");
    }

    private void locatiiFaraAngajati() throws SQLException {
        System.out.println("\nLocații Fără Angajați:");
        locatieService.findLocatiiFaraAngajati().forEach(loc ->
                System.out.println(loc.getOras() + ", " + loc.getTara()));
        audit.log("Obținerea Locațiilor Fără Angajați");
    }
}
