package repository;

import interfaces.IRepository;
import models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VehiculRepository implements IRepository<Vehicul> {
    private Connection connection;

    public VehiculRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Vehicul vehicul) throws SQLException {
        String query = "INSERT INTO Vehicul (idVehicul, numarInmatriculare, model, idAngajat, culoare, tipVehicul) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, vehicul.getIdVehicul());
            stmt.setString(2, vehicul.getNumarInmatriculare());
            stmt.setString(3, vehicul.getModel());
            stmt.setLong(4, vehicul.getIdAngajat());
            stmt.setString(5, vehicul.getCuloare());
            stmt.setString(6, vehicul.getTipVehicul());
            stmt.executeUpdate();
        }
    }

    public Vehicul findById(long id) throws SQLException {
        String query = "SELECT * FROM Vehicul WHERE idVehicul = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new Vehicul(
                            resultSet.getLong("idVehicul"),
                            resultSet.getString("numarInmatriculare"),
                            resultSet.getString("model"),
                            null,
                            resultSet.getLong("idAngajat"),
                            resultSet.getString("culoare"),
                            resultSet.getString("tipVehicul")
                    );
                }
            }
        }
        return null;
    }

    public List<Vehicul> findAll() throws SQLException {
        List<Vehicul> vehicule = new ArrayList<>();
        String query = "SELECT * FROM Vehicul";
        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                vehicule.add(new Vehicul(
                        resultSet.getLong("idVehicul"),
                        resultSet.getString("numarInmatriculare"),
                        resultSet.getString("model"),
                        null,
                        resultSet.getLong("idAngajat"),// Setăm Angajatul la null
                        resultSet.getString("culoare"),
                        resultSet.getString("tipVehicul")
                ));
            }
        }
        return vehicule;
    }

    public void update(Vehicul vehicul) throws SQLException {
        String query = "UPDATE Vehicul SET numarInmatriculare = ?, model = ?, idAngajat = ?, culoare = ?, tipVehicul = ? WHERE idVehicul = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicul.getNumarInmatriculare());
            stmt.setString(2, vehicul.getModel());
            stmt.setLong(3, vehicul.getIdAngajat());
            stmt.setString(4, vehicul.getCuloare());
            stmt.setString(5, vehicul.getTipVehicul());
            stmt.setLong(6, vehicul.getIdVehicul());
            stmt.executeUpdate();
        }
    }

    public void delete(long id) throws SQLException {
        String query = "DELETE FROM Vehicul WHERE idVehicul = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Vehicul findByIdWithAngajatAndRezervareVehicul(long id) throws SQLException {
        String query = """
        SELECT v.idVehicul, v.numarInmatriculare, v.model AS vehicul_model, v.culoare, v.tipVehicul, v.idAngajat,
               a.idAngajat AS angajat_id, a.numeAngajat AS angajat_nume, a.prenumeAngajat AS angajat_prenume,
               a.nrTelefon AS angajat_telefon, a.emailAngajat AS angajat_email, a.functie AS angajat_functie, a.idLocatie AS angajat_idLocatie,
               rv.idRezervareVehicul AS rezervareVehicul_id, rv.idRezervare AS rezervareVehicul_idRezervare,
               rv.idVehicul AS rezervareVehicul_idVehicul,
               rv.startDate AS rezervareVehicul_startDate, rv.endDate AS rezervareVehicul_endDate
        FROM Vehicul v
        LEFT JOIN Angajat a ON v.idAngajat = a.idAngajat
        LEFT JOIN RezervareVehicul rv ON v.idVehicul = rv.idVehicul
        WHERE v.idVehicul = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Vehicul vehicul = null;
                List<models.RezervareVehicul> rezervari = new ArrayList<>();

                while (rs.next()) {
                    if (vehicul == null) {
                        Angajat angajat = null;
                        long angajatId = rs.getLong("angajat_id");
                        if (!rs.wasNull()) {
                            angajat = new Angajat(
                                    angajatId,
                                    rs.getString("angajat_nume"),
                                    rs.getString("angajat_prenume"),
                                    rs.getString("angajat_telefon"),
                                    rs.getString("angajat_email"),
                                    rs.getString("angajat_functie"),
                                    null,
                                    rs.getLong("angajat_idLocatie")
                            );
                        }

                        vehicul = new Vehicul(
                                rs.getLong("idVehicul"),
                                rs.getString("numarInmatriculare"),
                                rs.getString("vehicul_model"),
                                angajat,
                                rs.getLong("idAngajat"),
                                rs.getString("culoare"),
                                rs.getString("tipVehicul")
                        );
                    }

                    long rezervareVehiculId = rs.getLong("rezervareVehicul_id");
                    if (!rs.wasNull()) {
                        models.RezervareVehicul rezervareVehicul = new models.RezervareVehicul(
                                rezervareVehiculId,
                                null,
                                null,
                                rs.getLong("rezervareVehicul_idRezervare"),
                                rs.getLong("rezervareVehicul_idVehicul"),
                                rs.getDate("rezervareVehicul_startDate"),
                                rs.getDate("rezervareVehicul_endDate")
                        );
                        rezervari.add(rezervareVehicul);
                    }
                }

                if (vehicul != null) {
                    vehicul.setRezervari(rezervari);
                }

                return vehicul;
            }
        }
    }

    public Double getMediaRecenziiPentruVehicul(long idVehicul) throws SQLException {
        String query = """
            SELECT AVG(r.nrStele) AS mediaStele
            FROM Vehicul v
            LEFT JOIN RezervareVehicul rv ON v.idVehicul = rv.idVehicul
            LEFT JOIN Rezervare rez ON rv.idRezervare = rez.idRezervare
            LEFT JOIN Recenzie r ON rez.idRezervare = r.idRezervare
            WHERE v.idVehicul = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, idVehicul);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("mediaStele");
                }
            }
        }
        return null;
    }

    public Map<Vehicul, Double> findTop5VehiculeCuCeaMaiMareMedieRatingMinim5Recenzii() throws SQLException {
        String query = """
        SELECT v.idVehicul, v.numarInmatriculare, v.model, v.culoare, v.tipVehicul, v.idAngajat,
               AVG(r.nrStele) AS mediaStele, COUNT(r.idRecenzie) AS numarRecenzii
        FROM Vehicul v
        JOIN RezervareVehicul rv ON v.idVehicul = rv.idVehicul
        JOIN Rezervare rez ON rv.idRezervare = rez.idRezervare
        JOIN Recenzie r ON rez.idRezervare = r.idRezervare
        GROUP BY v.idVehicul, v.numarInmatriculare, v.model, v.culoare, v.tipVehicul, v.idAngajat
        HAVING COUNT(r.idRecenzie) >= 5
        ORDER BY mediaStele DESC
        LIMIT 5
        """;

        Map<Vehicul, Double> topVehicule = new LinkedHashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vehicul vehicul = new Vehicul(
                        rs.getLong("idVehicul"),
                        rs.getString("numarInmatriculare"),
                        rs.getString("model"),
                        null,
                        rs.getLong("idAngajat"),
                        rs.getString("culoare"),
                        rs.getString("tipVehicul")
                );

                double mediaStele = rs.getDouble("mediaStele");
                topVehicule.put(vehicul, mediaStele);
            }
        }

        return topVehicule;
    }


    public Vehicul mapRowToVehicul(ResultSet rs) throws SQLException {
        String tipVehicul = rs.getString("tipVehicul");

        long idVehicul = rs.getLong("idVehicul");
        String numarInmatriculare = rs.getString("numarInmatriculare");
        String model = rs.getString("model");
        Angajat angajat = null;
        long idAngajat = rs.getLong("idAngajat");
        String culoare = rs.getString("culoare");

        switch (tipVehicul.toLowerCase()) {
            case "masina":
                return new Masina(idVehicul, numarInmatriculare, model, angajat, idAngajat, culoare, tipVehicul);
            case "bicicleta":
                return new Bicicleta(idVehicul, numarInmatriculare, model, angajat, idAngajat, culoare, tipVehicul);
            case "camion":
                return new Camion(idVehicul, numarInmatriculare, model, angajat, idAngajat, culoare, tipVehicul);
            default:
                return new Vehicul(idVehicul, numarInmatriculare, model, angajat, idAngajat, culoare, tipVehicul);
        }
    }

    public Vehicul findByIdWithCopy(long id) throws SQLException {
        String query = "SELECT * FROM Vehicul WHERE idVehicul = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToVehicul(rs);
                }
            }
        }

        return null;
    }

    public List<Vehicul> findVehiculeFaraRezervari() throws SQLException {
        List<Vehicul> vehicule = new ArrayList<>();
        String query = """
        SELECT * FROM Vehicul v
        WHERE NOT EXISTS (
            SELECT 1
            FROM RezervareVehicul rv
            WHERE rv.idVehicul = v.idVehicul
        )
    """;

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                vehicule.add(mapRowToVehicul(rs));
            }
        }

        return vehicule;
    }


    public Map<String, Integer> getDistributieVehiculePeLocatii() throws SQLException {
        String query = """
        SELECT l.tara, l.oras, l.strada, l.nrStrada, COUNT(v.idVehicul) AS numarVehicule
        FROM Vehicul v
        JOIN Angajat a ON v.idAngajat = a.idAngajat
        JOIN Locatie l ON a.idLocatie = l.idLocatie
        GROUP BY l.tara, l.oras, l.strada, l.nrStrada
        ORDER BY numarVehicule DESC
    """;

        Map<String, Integer> distributie = new LinkedHashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String locatie = String.format("%s, %s, %s %d",
                        rs.getString("tara"),
                        rs.getString("oras"),
                        rs.getString("strada"),
                        rs.getInt("nrStrada")
                );

                int numarVehicule = rs.getInt("numarVehicule");
                distributie.put(locatie, numarVehicule);
            }
        }

        return distributie;
    }

}
