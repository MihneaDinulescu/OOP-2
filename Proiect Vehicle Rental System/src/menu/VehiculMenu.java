package menu;

import models.Vehicul;
import service.VehiculService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class VehiculMenu {
    private final VehiculService vehiculService;
    private final Scanner scanner;

    public VehiculMenu(Connection connection, Scanner scanner) {
        this.vehiculService = VehiculService.getInstance(connection);
        this.scanner = scanner;
    }

    public void showMenu() {
        int option = -1;
        while (option != 0) {
            System.out.println("\n--- Meniu Vehicul ---");
            System.out.println("1. Adaugă vehicul");
            System.out.println("2. Afișează toate vehiculele");
            System.out.println("3. Caută vehicul după ID");
            System.out.println("4. Actualizează vehicul");
            System.out.println("5. Șterge vehicul");
            System.out.println("0. Înapoi");
            System.out.print("Alege o opțiune: ");

            try {
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1 -> addVehicul();
                    case 2 -> afiseazaToateVehiculele();
                    case 3 -> cautaVehiculById();
                    case 4 -> updateVehicul();
                    case 5 -> deleteVehicul();
                    case 0 -> System.out.println("Înapoi la meniul principal...");
                    default -> System.out.println("Opțiune invalidă!");
                }
            } catch (NumberFormatException | SQLException e) {
                System.out.println("Eroare: " + e.getMessage());
            }
        }
    }

    private void addVehicul() throws SQLException {
        System.out.print("Număr înmatriculare: ");
        String numarInmatriculare = scanner.nextLine();

        System.out.print("Model: ");
        String model = scanner.nextLine();

        System.out.print("ID Angajat: ");
        long idAngajat = Long.parseLong(scanner.nextLine());

        System.out.print("Culoare: ");
        String culoare = scanner.nextLine();

        System.out.print("Tip vehicul: ");
        String tipVehicul = scanner.nextLine();

        Vehicul vehicul = new Vehicul(0, numarInmatriculare, model, null, idAngajat, culoare, tipVehicul);
        vehiculService.addVehicul(vehicul);

        System.out.println("Vehicul adăugat cu succes!");
    }

    private void afiseazaToateVehiculele() throws SQLException {
        List<Vehicul> vehicule = vehiculService.getAllVehicule();
        if (vehicule.isEmpty()) {
            System.out.println("Nu există vehicule.");
        } else {
            for (Vehicul v : vehicule) {
                System.out.println("ID Vehicul: " + v.getIdVehicul() +
                        ", Număr înmatriculare: " + v.getNumarInmatriculare() +
                        ", Model: " + v.getModel() +
                        ", ID Angajat: " + v.getIdAngajat() +
                        ", Culoare: " + v.getCuloare() +
                        ", Tip vehicul: " + v.getTipVehicul());
            }
        }
    }

    private void cautaVehiculById() throws SQLException {
        System.out.print("Introdu ID vehicul: ");
        long id = Long.parseLong(scanner.nextLine());

        Vehicul v = vehiculService.getVehiculById(id);
        if (v != null) {
            System.out.println("ID Vehicul: " + v.getIdVehicul() +
                    ", Număr înmatriculare: " + v.getNumarInmatriculare() +
                    ", Model: " + v.getModel() +
                    ", ID Angajat: " + v.getIdAngajat() +
                    ", Culoare: " + v.getCuloare() +
                    ", Tip vehicul: " + v.getTipVehicul());
        } else {
            System.out.println("Vehiculul nu a fost găsit.");
        }
    }

    private void updateVehicul() throws SQLException {
        System.out.print("Introdu ID vehicul pentru actualizare: ");
        long id = Long.parseLong(scanner.nextLine());

        Vehicul v = vehiculService.getVehiculById(id);
        if (v == null) {
            System.out.println("Vehiculul nu a fost găsit.");
            return;
        }

        System.out.print("Noul număr înmatriculare (curent: " + v.getNumarInmatriculare() + "): ");
        String numarInmatriculare = scanner.nextLine();

        System.out.print("Noul model (curent: " + v.getModel() + "): ");
        String model = scanner.nextLine();

        System.out.print("Noul ID Angajat (curent: " + v.getIdAngajat() + "): ");
        long idAngajat = Long.parseLong(scanner.nextLine());

        System.out.print("Noua culoare (curent: " + v.getCuloare() + "): ");
        String culoare = scanner.nextLine();

        System.out.print("Noul tip vehicul (curent: " + v.getTipVehicul() + "): ");
        String tipVehicul = scanner.nextLine();

        // Actualizare câmpuri
        v = new Vehicul(v.getIdVehicul(), numarInmatriculare, model, null, idAngajat, culoare, tipVehicul);
        vehiculService.updateVehicul(v);

        System.out.println("Vehicul actualizat cu succes!");
    }

    private void deleteVehicul() throws SQLException {
        System.out.print("Introdu ID vehicul pentru ștergere: ");
        long id = Long.parseLong(scanner.nextLine());

        vehiculService.deleteVehicul(id);
        System.out.println("Vehicul șters cu succes!");
    }
}
