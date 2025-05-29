import models.*;
import service.*;
import config.ConnectionProvider;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection connection = ConnectionProvider.getConnection()) {

            // Inițializare servicii
            AngajatService angajatService = AngajatService.getInstance(connection);
            LocatieService locatieService = LocatieService.getInstance(connection);
            ClientService clientService = ClientService.getInstance(connection);
            VehiculService vehiculService = VehiculService.getInstance(connection);
            RezervareService rezervareService = RezervareService.getInstance(connection);
            RezervareVehiculService rezervareVehiculService = RezervareVehiculService.getInstance(connection);
            RecenzieService recenzieService = RecenzieService.getInstance(connection);
            AuditService audit = AuditService.getInstance();

            // 1. Top 3 clienți cu cele mai multe rezervări
            System.out.println("Top 3 Clienți cu Cele Mai Multe Rezervări:");
            List<Client> topClienti = clientService.getTop3ClientiCuCeleMaiMulteRezervari();
            for (Client c : topClienti) {
                System.out.println("- " + c.getNume() + " " + c.getPrenume() + " | Telefon: " + c.getNumarTelefon());
            }
            audit.log("Top 3 Clienți cu Cele Mai Multe Rezervări");

            // 2. Vehicule disponibile într-o perioadă
            Date startDate = Date.valueOf("2025-06-01");
            Date endDate = Date.valueOf("2025-06-10");
            List<Vehicul> vehiculeDisponibile = vehiculService.getVehiculeDisponibile(startDate, endDate);
            System.out.println("\nVehicule Disponibile între " + startDate + " și " + endDate + ":");
            vehiculeDisponibile.forEach(v -> System.out.println(" - " + v.getModel() + " (" + v.getNumarInmatriculare() + ")"));
            audit.log("Vehicule Disponibile în Perioada " + startDate + " - " + endDate);

            // 3. Angajatul cu cele mai multe vehicule
            Angajat topAngajat = angajatService.findAngajatCuCeleMaiMulteVehicule();
            if (topAngajat != null) {
                System.out.println("\nAngajatul cu Cele Mai Multe Vehicule:");
                System.out.println(topAngajat.getNumeAngajat() + " " + topAngajat.getPrenumeAngajat());
            }
            audit.log("Angajatul cu Cele Mai Multe Vehicule");

            // 4. Mapare client -> număr rezervări
            System.out.println("\nClienți și Numărul Total de Rezervări:");
            Map<Client, Integer> clientiCuNrRezervari = clientService.getClientiCuNumarRezervari();
            clientiCuNrRezervari.forEach((client, nr) ->
                    System.out.println(client.getNume() + " " + client.getPrenume() + " - " + nr + " rezervări"));
            audit.log("Mapare Client cu Număr Total de Rezervări");

            // 5. Media recenzii per vehicul
            Double mediaRecenzii = vehiculService.getMediaRecenziiPentruVehicul(1);
            System.out.println("\nMedia Recenziilor pentru Vehiculul cu ID 1: " +
                    (mediaRecenzii != null ? mediaRecenzii : "Nu există recenzii"));
            audit.log("Media Recenzii per Vehicul");

            // 6. Angajați, locații și număr total vehicule
            System.out.println("\nAngajați și Locațiile Lor cu Numărul Total de Vehicule Gestionate:");
            Map<Angajat, Integer> angajatiCuVehicule = angajatService.findAllWithNumarVehicule();
            angajatiCuVehicule.forEach((a, nr) ->
                    System.out.println(a.getNumeAngajat() + " - " + a.getLocatie().getOras() + ": " + nr + " vehicule"));
            audit.log("Angajați și Locațiile Lor cu Numărul Total de Vehicule");

            // 7. Top 5 vehicule după medie rating și minim 5 recenzii
            System.out.println("\nTop 5 Vehicule cu Cea Mai Mare Medie a Ratingului (Minim 5 Recenzii):");
            Map<Vehicul, Double> top5 = vehiculService.getTop5VehiculeCuCeaMaiMareMedieRatingMinim5Recenzii();
            int i = 1;
            for (Map.Entry<Vehicul, Double> entry : top5.entrySet()) {
                System.out.printf("%d. %s (%s) - %.2f%n", i++, entry.getKey().getModel(), entry.getKey().getNumarInmatriculare(), entry.getValue());
            }
            audit.log("Top 5 Vehicule cu Cea Mai Mare Medie a Ratingului și Minim 5 Recenzii");

            // 8. Clienți cu avans maxim
            System.out.println("\nClienți și Avansul Maxim Plătit într-o Rezervare:");
            clientService.getClientsWithMaxAvansMap().forEach((client, avans) ->
                    System.out.println(client.getNume() + " " + client.getPrenume() + " - Avans maxim: " + avans));
            audit.log("Afișare Clienți cu Avans Maxim");

            // 9. Total plată pentru o rezervare
            double totalPlata = rezervareService.calculeazaTotalPlataPerRezervare(1L);
            System.out.println("\nTotal de Plată pentru Rezervarea cu ID 1: " + totalPlata);
            audit.log("Calculul Totalului de Plată pe Rezervare");

            // 10. Locații cu numărul lor de rezervări
            System.out.println("\nLocații și Numărul de Rezervări:");
            locatieService.getLocatiiCuNumarRezervari().forEach((loc, nr) ->
                    System.out.println(loc.getOras() + ", " + loc.getTara() + " - " + nr + " rezervări"));
            audit.log("Locațiile cu Numărul Lor de Rezervări");

            // 11. Vehicule care nu au fost rezervate niciodată
            System.out.println("\nVehicule care Nu au Fost Rezervate Niciodată:");
            List<Vehicul> faraRezervari = vehiculService.getVehiculeFaraRezervari();
            faraRezervari.forEach(v ->
                    System.out.println(v.getModel() + " (" + v.getNumarInmatriculare() + ")"));
            audit.log("Vehicule care Nu au Fost Rezervate Niciodată");

            // 12. Număr rezervări pe lunile anului
            System.out.println("\nNumăr de Rezervări pe Lunile Anului:");
            rezervareService.getNumarRezervariLunare().forEach((luna, nr) ->
                    System.out.println("Luna: " + luna + " - " + nr + " rezervări"));
            audit.log("Număr de Rezervări pe Lunile Anului");

            // 13. Distribuția vehiculelor pe locații
            System.out.println("\nDistribuția Vehiculelor pe Locații:");
            vehiculService.getDistributieVehiculePeLocatii().forEach((loc, nr) ->
                    System.out.println("Locație: " + loc + " - " + nr + " vehicule"));
            audit.log("Distribuția Vehiculelor per Locație");

            // 14. Recenzii negative
            System.out.println("\nRecenzii Negative:");
            recenzieService.getRecenziiNegative().forEach(r ->
                    System.out.println("ID: " + r.getIdRecenzie() + " - Stele: " + r.getNrStele() + " - Data: " + r.getDataRecenzie()));
            audit.log("Obținerea Recenziilor Negative");

            // 15. Locații fără angajați
            System.out.println("\nLocații Fără Angajați:");
            locatieService.findLocatiiFaraAngajati().forEach(loc ->
                    System.out.println(loc.getOras() + ", " + loc.getTara()));
            audit.log("Obținerea Locațiilor Fără Angajați");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
