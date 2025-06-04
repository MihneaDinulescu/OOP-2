package repository;

import interfaces.IRepository;
import models.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRepository implements IRepository<Client> {
    private Connection connection;

    public ClientRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Client client) throws SQLException {
        String query = "INSERT INTO Client (idClient, nume, prenume, numarTelefon, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, client.getIdClient());
            stmt.setString(2, client.getNume());
            stmt.setString(3, client.getPrenume());
            stmt.setString(4, client.getNumarTelefon());
            stmt.setString(5, client.getEmail());
            stmt.executeUpdate();
        }
    }

    public Client findById(long id) throws SQLException {
        String query = "SELECT * FROM Client WHERE idClient = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new Client(
                            resultSet.getLong("idClient"),
                            resultSet.getString("nume"),
                            resultSet.getString("prenume"),
                            resultSet.getString("numarTelefon"),
                            resultSet.getString("email")
                    );
                }
            }
        }
        return null;
    }

    public List<Client> findAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM Client";
        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                clients.add(new Client(
                        resultSet.getLong("idClient"),
                        resultSet.getString("nume"),
                        resultSet.getString("prenume"),
                        resultSet.getString("numarTelefon"),
                        resultSet.getString("email")
                ));
            }
        }
        return clients;
    }

    public void update(Client client) throws SQLException {
        String query = "UPDATE Client SET nume = ?, prenume = ?, numarTelefon = ?, email = ? WHERE idClient = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, client.getNume());
            stmt.setString(2, client.getPrenume());
            stmt.setString(3, client.getNumarTelefon());
            stmt.setString(4, client.getEmail());
            stmt.setLong(5, client.getIdClient());
            stmt.executeUpdate();
        }
    }

    public void delete(long id) throws SQLException {
        String query = "DELETE FROM Client WHERE idClient = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Client findByIdWithRezervari(long id) throws SQLException {
        String query = """
        
                SELECT c.idClient, c.nume, c.prenume, c.numarTelefon, c.email,
               r.idRezervare, r.idClient AS r_idClient, r.avans, r.metodaPlata
        FROM Client c
        LEFT JOIN Rezervare r ON c.idClient = r.idClient
        WHERE c.idClient = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Client client = null;
                List<models.Rezervare> rezervari = new ArrayList<>();

                while (rs.next()) {
                    if (client == null) {
                        client = new Client(
                                rs.getLong("idClient"),
                                rs.getString("nume"),
                                rs.getString("prenume"),
                                rs.getString("numarTelefon"),
                                rs.getString("email")
                        );
                    }

                    long rezervareId = rs.getLong("idRezervare");
                    if (!rs.wasNull()) {
                        models.Rezervare rezervare = new models.Rezervare(
                                rezervareId,
                                null,  // client = null cum ai cerut
                                rs.getLong("r_idClient"),
                                rs.getDouble("avans"),
                                rs.getString("metodaPlata")
                        );
                        rezervari.add(rezervare);
                    }
                }

                if (client != null) {
                    client.setRezervari(rezervari);
                }

                return client;
            }
        }
    }

    public List<Client> findTop3ByRezervari() throws SQLException {
        List<Client> topClients = new ArrayList<>();
        String query = """
        SELECT c.idClient, c.nume, c.prenume, c.numarTelefon, c.email, COUNT(r.idRezervare) AS
                nr_rezervari
        FROM
                Client c
        JOIN Rezervare r ON c.idClient
                = r.idClient
        GROUP BY c.idClient, c.nume, c.prenume, c.
                numarTelefon, c.email
        ORDER BY nr_rezervari DESC
        LIMIT 3
    """;

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Client client = new Client(
                        rs.getLong("idClient"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("numarTelefon"),
                        rs.getString("email")
                );
                topClients.add(client);
            }
        }

        return topClients;
    }

    public Map<Client, Double> findClientsWithMaxAvansMap() throws SQLException {
        String query = """
        SELECT c.idClient, c.nume, c.prenume, c.
                numarTelefon, c.email,
               (SELECT MAX(r.avans) 
                FROM Rezervare r 
                WHERE r.idClient = c.idClient) AS
                maxAvans
        FROM Client c
    """;

        Map<Client, Double> resultMap = new HashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Client client = new Client(
                        rs.getLong("idClient"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("numarTelefon"),
                        rs.getString("email")
                );

                double maxAvans = rs.getDouble("maxAvans");
                resultMap.put(client, maxAvans);
            }
        }

        return resultMap;
    }

}

