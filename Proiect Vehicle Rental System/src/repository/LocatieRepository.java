package repository;

import interfaces.IRepository;
import models.Angajat;
import models.Locatie;
import java.sql.*;
import java.util.*;

public class LocatieRepository implements IRepository<Locatie> {
    private final Connection connection;

    public LocatieRepository(Connection connection) {
        this.connection = connection;
    }

    // Adaugă o locație
    public void save(Locatie locatie) throws SQLException {
        String query = "INSERT INTO Locatie (idLocatie, tara, oras, strada, nrStrada) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, locatie.getIdLocatie());
            stmt.setString(2, locatie.getTara());
            stmt.setString(3, locatie.getOras());
            stmt.setString(4, locatie.getStrada());
            stmt.setInt(5, locatie.getNrStrada());
            stmt.executeUpdate();
        }
    }

    // Căutare locație după ID
    public Locatie findById(long id) throws SQLException {
        String query = "SELECT * FROM Locatie WHERE idLocatie = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new Locatie(
                            resultSet.getLong("idLocatie"),
                            resultSet.getString("tara"),
                            resultSet.getString("oras"),
                            resultSet.getString("strada"),
                            resultSet.getInt("nrStrada")
                    );
                }
            }
        }
        return null;
    }

    // Obține toate locațiile
    public List<Locatie> findAll() throws SQLException {
        List<Locatie> locatii = new ArrayList<>();
        String query = "SELECT * FROM Locatie";
        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                locatii.add(new Locatie(
                        resultSet.getLong("idLocatie"),
                        resultSet.getString("tara"),
                        resultSet.getString("oras"),
                        resultSet.getString("strada"),
                        resultSet.getInt("nrStrada")
                ));
            }
        }
        return locatii;
    }

    // Actualizează o locație
    public void update(Locatie locatie) throws SQLException {
        String query = "UPDATE Locatie SET tara = ?, oras = ?, strada = ?, nrStrada = ? WHERE idLocatie = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, locatie.getTara());
            stmt.setString(2, locatie.getOras());
            stmt.setString(3, locatie.getStrada());
            stmt.setInt(4, locatie.getNrStrada());
            stmt.setLong(5, locatie.getIdLocatie());
            stmt.executeUpdate();
        }
    }

    // Șterge o locație
    public void delete(long id) throws SQLException {
        String query = "DELETE FROM Locatie WHERE idLocatie = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Locatie findByIdWithAngajati(long id) throws SQLException {
        String query = """
        SELECT l.idLocatie, l.tara, l.oras, l.strada, l.nrStrada,
               a.idAngajat, a.numeAngajat, a.prenumeAngajat, a.nrTelefon, a.emailAngajat, a.functie, a.idLocatie AS angajatLocatieId
        FROM Locatie l
        LEFT JOIN Angajat a ON l.idLocatie = a.idLocatie
        WHERE l.idLocatie = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Locatie locatie = null;
                List<Angajat> angajati = new ArrayList<>();

                while (rs.next()) {
                    if (locatie == null) {
                        locatie = new Locatie(
                                rs.getLong("idLocatie"),
                                rs.getString("tara"),
                                rs.getString("oras"),
                                rs.getString("strada"),
                                rs.getInt("nrStrada")
                        );
                    }

                    long angajatId = rs.getLong("idAngajat");
                    if (!rs.wasNull()) {
                        Angajat angajat = new Angajat(
                                angajatId,
                                rs.getString("numeAngajat"),
                                rs.getString("prenumeAngajat"),
                                rs.getString("nrTelefon"),
                                rs.getString("emailAngajat"),
                                rs.getString("functie"),
                                null,  // locatie lasat null aici
                                rs.getLong("angajatLocatieId")
                        );
                        angajati.add(angajat);
                    }
                }

                if (locatie != null) {
                    locatie.setAngajati(angajati);
                }

                return locatie;
            }
        }
    }

    public Map<Locatie, Integer> findLocatiiCuNumarRezervari() throws SQLException {
        String query = """
        SELECT l.idLocatie, l.tara, l.oras, l.strada, l.nrStrada, COUNT(r.idRezervare) AS numarRezervari
        FROM Locatie l
        LEFT JOIN Angajat a ON a.idLocatie = l.idLocatie
        LEFT JOIN Vehicul v ON v.idAngajat = a.idAngajat
        LEFT JOIN RezervareVehicul rv ON rv.idVehicul = v.idVehicul
        LEFT JOIN Rezervare r ON r.idRezervare = rv.idRezervare
        GROUP BY l.idLocatie, l.tara, l.oras, l.strada, l.nrStrada
        ORDER BY numarRezervari DESC
    """;

        Map<Locatie, Integer> rezultat = new LinkedHashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Locatie locatie = new Locatie(
                        rs.getLong("idLocatie"),
                        rs.getString("tara"),
                        rs.getString("oras"),
                        rs.getString("strada"),
                        rs.getInt("nrStrada")
                );
                int nrRezervari = rs.getInt("numarRezervari");
                rezultat.put(locatie, nrRezervari);
            }
        }

        return rezultat;
    }

    public List<Locatie> findLocatiiFaraAngajati() throws SQLException {
        List<Locatie> locatiiFaraAngajati = new ArrayList<>();
        String query = """
            SELECT l.idLocatie, l.tara, l.oras, l.strada, l.nrStrada
            FROM Locatie l
            LEFT JOIN Angajat a ON l.idLocatie = a.idLocatie
            WHERE a.idAngajat IS NULL
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Locatie locatie = new Locatie(
                        rs.getLong("idLocatie"),
                        rs.getString("tara"),
                        rs.getString("oras"),
                        rs.getString("strada"),
                        rs.getInt("nrStrada")
                );
                locatiiFaraAngajati.add(locatie);
            }
        }
        return locatiiFaraAngajati;
    }


}
