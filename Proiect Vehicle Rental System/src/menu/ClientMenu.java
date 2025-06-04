package menu;

import models.Client;
import service.ClientService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ClientMenu {
    private final ClientService clientService;
    private final Scanner scanner;

    public ClientMenu(Connection connection, Scanner scanner) {
        this.clientService = ClientService.getInstance(connection);
        this.scanner = scanner;
    }

    public void afiseazaMeniu() throws SQLException {
        int optiune;
        do {
            System.out.println("\n--- Meniu Client ---");
            System.out.println("1. Adaugă client");
            System.out.println("2. Afișează toți clienții");
            System.out.println("3. Caută client după ID");
            System.out.println("4. Actualizează client");
            System.out.println("5. Șterge client");
            System.out.println("0. Înapoi la meniul principal");
            System.out.print("Alege o opțiune: ");

            optiune = Integer.parseInt(scanner.nextLine());

            switch (optiune) {
                case 1 -> adaugaClient();
                case 2 -> afiseazaTotiClientii();
                case 3 -> cautaClientDupaId();
                case 4 -> actualizeazaClient();
                case 5 -> stergeClient();
                case 0 -> System.out.println("Înapoi la meniul principal...");
                default -> System.out.println("Opțiune invalidă!");
            }
        } while (optiune != 0);
    }

    private void adaugaClient() throws SQLException {
        System.out.println("Introdu datele clientului:");
        System.out.print("Nume: ");
        String nume = scanner.nextLine();

        System.out.print("Prenume: ");
        String prenume = scanner.nextLine();

        System.out.print("Număr telefon: ");
        String numarTelefon = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        Client client = new Client(0, nume, prenume, numarTelefon, email);
        clientService.addClient(client);
        System.out.println("Client adăugat cu succes!");
    }

    private void afiseazaTotiClientii() throws SQLException {
        List<Client> clienti = clientService.getAllClients();
        if (clienti.isEmpty()) {
            System.out.println("Nu există clienți.");
        } else {
            clienti.forEach(c -> System.out.println(
                    "ID: " + c.getIdClient() +
                            ", Nume: " + c.getNume() +
                            ", Prenume: " + c.getPrenume() +
                            ", Telefon: " + c.getNumarTelefon() +
                            ", Email: " + c.getEmail()
            ));
        }
    }

    private void cautaClientDupaId() throws SQLException {
        System.out.print("Introdu ID client: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            Client client = clientService.getClientById(id);
            if (client != null) {
                System.out.println("ID: " + client.getIdClient() +
                        ", Nume: " + client.getNume() +
                        ", Prenume: " + client.getPrenume() +
                        ", Telefon: " + client.getNumarTelefon() +
                        ", Email: " + client.getEmail());
            } else {
                System.out.println("Clientul nu a fost găsit.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID invalid.");
        }
    }

    private void actualizeazaClient() throws SQLException {
        System.out.print("Introdu ID-ul clientului de actualizat: ");
        long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID invalid.");
            return;
        }

        Client clientExist = null;
        try {
            clientExist = clientService.getClientById(id);
        } catch (Exception ignored) {}

        if (clientExist == null) {
            System.out.println("Clientul nu a fost găsit.");
            return;
        }

        System.out.print("Nume nou: ");
        String nume = scanner.nextLine();

        System.out.print("Prenume nou: ");
        String prenume = scanner.nextLine();

        System.out.print("Număr telefon nou: ");
        String numarTelefon = scanner.nextLine();

        System.out.print("Email nou: ");
        String email = scanner.nextLine();

        Client client = new Client(id, nume, prenume, numarTelefon, email);
        clientService.updateClient(client);
        System.out.println("Client actualizat cu succes!");
    }

    private void stergeClient() throws SQLException {
        System.out.print("Introdu ID-ul clientului de șters: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            clientService.deleteClient(id);
            System.out.println("Client șters cu succes!");
        } catch (NumberFormatException e) {
            System.out.println("ID invalid.");
        }
    }
}
