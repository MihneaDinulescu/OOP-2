package repository;

import interfaces.IRepository;
import models.Angajat;
import models.Locatie;
import models.Vehicul;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.HashMap;

public class AngajatRepository implements IRepository<Angajat> {
    private Connection connection;

    public AngajatRepository(Connection connection) {
        this.connection = connection;
    }

    // Adaugă un angajat
    public void save(Angajat angajat) throws SQLException {
        String query = "INSERT INTO Angajat (idAngajat, numeAngajat, prenumeAngajat, nrTelefon, emailAngajat, functie, idLocatie) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, angajat.getIdAngajat());
            stmt.setString(2, angajat.getNumeAngajat());
            stmt.setString(3, angajat.getPrenumeAngajat());
            stmt.setString(4, angajat.getNrTelefon());
            stmt.setString(5, angajat.getEmailAngajat());
            stmt.setString(6, angajat.getFunctie());
            stmt.setLong(7, angajat.getIdLocatie());
            stmt.executeUpdate();
        }
    }

    // Obține un angajat după ID
    public Angajat findById(long id) throws SQLException {
        String query = "SELECT * FROM Angajat WHERE idAngajat = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Angajat(
                            rs.getLong("idAngajat"),
                            rs.getString("numeAngajat"),
                            rs.getString("prenumeAngajat"),
                            rs.getString("nrTelefon"),
                            rs.getString("emailAngajat"),
                            rs.getString("functie"),
                            null,
                            rs.getLong("idLocatie")
                    );
                }
            }
        }
        return null;
    }

    // Obține toți angajații
    public List<Angajat> findAll() throws SQLException {
        List<Angajat> angajati = new ArrayList<>();
        String query = "SELECT * FROM Angajat";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                angajati.add(new Angajat(
                        rs.getLong("idAngajat"),
                        rs.getString("numeAngajat"),
                        rs.getString("prenumeAngajat"),
                        rs.getString("nrTelefon"),
                        rs.getString("emailAngajat"),
                        rs.getString("functie"),
                        null,
                        rs.getLong("idLocatie")
                ));
            }
        }
        return angajati;
    }

    // Actualizează un angajat
    public void update(Angajat angajat) throws SQLException {
        String query = "UPDATE Angajat SET numeAngajat = ?, prenumeAngajat = ?, nrTelefon = ?, emailAngajat = ?, functie = ?, idLocatie = ? WHERE idAngajat = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, angajat.getNumeAngajat());
            stmt.setString(2, angajat.getPrenumeAngajat());
            stmt.setString(3, angajat.getNrTelefon());
            stmt.setString(4, angajat.getEmailAngajat());
            stmt.setString(5, angajat.getFunctie());
            stmt.setLong(6, angajat.getIdLocatie());
            stmt.setLong(7, angajat.getIdAngajat());
            stmt.executeUpdate();
        }
    }

    // Șterge un angajat
    public void delete(long id) throws SQLException {
        String query = "DELETE FROM Angajat WHERE idAngajat = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Angajat findByIdWithLocatieAndVehicule(long id) throws SQLException {
        String query = """
        SELECT a.*, 
               l.idLocatie AS l_idLocatie, l.tara, l.oras, l.strada, l.nrStrada,
               v.idVehicul, v.numarInmatriculare, v.model AS vehicul_model, 
               v.culoare, v.tipVehicul
        FROM Angajat a
        LEFT JOIN Locatie l ON a.idLocatie = l.idLocatie
        LEFT JOIN Vehicul v ON a.idAngajat = v.idAngajat
        WHERE a.idAngajat = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Angajat angajat = null;
                List<Vehicul> vehicule = new ArrayList<>();

                while (rs.next()) {
                    if (angajat == null) {
                        Locatie locatie = null;
                        if (rs.getObject("l_idLocatie") != null) {
                            locatie = new Locatie(
                                    rs.getLong("l_idLocatie"),
                                    rs.getString("tara"),
                                    rs.getString("oras"),
                                    rs.getString("strada"),
                                    rs.getInt("nrStrada")
                            );
                        }

                        angajat = new Angajat(
                                rs.getLong("idAngajat"),
                                rs.getString("numeAngajat"),
                                rs.getString("prenumeAngajat"),
                                rs.getString("nrTelefon"),
                                rs.getString("emailAngajat"),
                                rs.getString("functie"),
                                locatie,
                                rs.getLong("idLocatie")
                        );
                    }

                    long vehiculId = rs.getLong("idVehicul");
                    if (!rs.wasNull()) {
                        Vehicul vehicul = new Vehicul(
                                vehiculId,
                                rs.getString("numarInmatriculare"),
                                rs.getString("vehicul_model"),
                                null,  // angajatul este setat la null aici
                                rs.getLong("idAngajat"),
                                rs.getString("culoare"),
                                rs.getString("tipVehicul")
                        );
                        vehicule.add(vehicul);
                    }
                }

                if (angajat != null) {
                    angajat.setVehicule(vehicule);
                }

                return angajat;
            }
        }
    }

    public Angajat findAngajatCuCeleMaiMulteVehicule() throws SQLException {
        String query = """
        SELECT a.*, COUNT(v.idVehicul) AS nrVehicule
        FROM Angajat a
        LEFT JOIN Vehicul v ON a.idAngajat = v.idAngajat
        GROUP BY a.idAngajat
        ORDER BY nrVehicule DESC
        LIMIT 1
    """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Angajat(
                            rs.getLong("idAngajat"),
                            rs.getString("numeAngajat"),
                            rs.getString("prenumeAngajat"),
                            rs.getString("nrTelefon"),
                            rs.getString("emailAngajat"),
                            rs.getString("functie"),
                            null,
                            rs.getLong("idLocatie")
                    );
                }
            }
        }
        return null;
    }

    public Map<Angajat, Integer> findAllWithNumarVehicule() throws SQLException {
        Map<Angajat, Integer> rezultat = new HashMap<>();

        String query = """
        SELECT a.idAngajat, a.numeAngajat, a.prenumeAngajat, a.nrTelefon, a.emailAngajat, a.functie, a.idLocatie,
               l.tara, l.oras, l.strada, l.nrStrada,
               COUNT(v.idVehicul) AS numarVehicule
        FROM Angajat a
        LEFT JOIN Locatie l ON a.idLocatie = l.idLocatie
        LEFT JOIN Vehicul v ON a.idAngajat = v.idAngajat
        GROUP BY a.idAngajat, a.numeAngajat, a.prenumeAngajat, a.nrTelefon, a.emailAngajat, a.functie, a.idLocatie,
                 l.tara, l.oras, l.strada, l.nrStrada
        ORDER BY numarVehicule DESC
    """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Locatie locatie = null;
                if (rs.getObject("idLocatie") != null) {
                    locatie = new Locatie(
                            rs.getLong("idLocatie"),
                            rs.getString("tara"),
                            rs.getString("oras"),
                            rs.getString("strada"),
                            rs.getInt("nrStrada")
                    );
                }

                Angajat angajat = new Angajat(
                        rs.getLong("idAngajat"),
                        rs.getString("numeAngajat"),
                        rs.getString("prenumeAngajat"),
                        rs.getString("nrTelefon"),
                        rs.getString("emailAngajat"),
                        rs.getString("functie"),
                        locatie,
                        rs.getLong("idLocatie")
                );

                int nrVehicule = rs.getInt("numarVehicule");

                rezultat.put(angajat, nrVehicule);
            }
        }

        return rezultat;
    }


}
