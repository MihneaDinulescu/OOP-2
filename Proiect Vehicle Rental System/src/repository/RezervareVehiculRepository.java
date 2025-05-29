package repository;

import interfaces.IRepository;
import models.RezervareVehicul;
import models.Vehicul;
import models.Rezervare;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RezervareVehiculRepository implements IRepository<RezervareVehicul> {
    private Connection connection;

    public RezervareVehiculRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(RezervareVehicul rezervareVehicul) throws SQLException {
        String query = "INSERT INTO RezervareVehicul (idRezervareVehicul, idRezervare, idVehicul, startDate, endDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, rezervareVehicul.getIdRezervareVehicul());
            stmt.setLong(2, rezervareVehicul.getIdRezervare());
            stmt.setLong(3, rezervareVehicul.getIdVehicul());
            stmt.setDate(4, new java.sql.Date(rezervareVehicul.getStartDate().getTime()));
            stmt.setDate(5, new java.sql.Date(rezervareVehicul.getEndDate().getTime()));
            stmt.executeUpdate();
        }
    }

    public RezervareVehicul findById(long id) throws SQLException {
        String query = "SELECT * FROM RezervareVehicul WHERE idRezervareVehicul = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new RezervareVehicul(
                            resultSet.getLong("idRezervareVehicul"),
                            null,
                            null,
                            resultSet.getLong("idRezervare"),
                            resultSet.getLong("idVehicul"),
                            resultSet.getDate("startDate"),
                            resultSet.getDate("endDate")
                    );
                }
            }
        }
        return null;
    }

    public List<RezervareVehicul> findAll() throws SQLException {
        List<RezervareVehicul> rezervariVehicul = new ArrayList<>();
        String query = "SELECT * FROM RezervareVehicul";
        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                rezervariVehicul.add(new RezervareVehicul(
                        resultSet.getLong("idRezervareVehicul"),
                        null,
                        null,
                        resultSet.getLong("idRezervare"),
                        resultSet.getLong("idVehicul"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate")
                ));
            }
        }
        return rezervariVehicul;
    }

    public void update(RezervareVehicul rezervareVehicul) throws SQLException {
        String query = "UPDATE RezervareVehicul SET idRezervare = ?, idVehicul = ?, startDate = ?, endDate = ? WHERE idRezervareVehicul = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, rezervareVehicul.getIdRezervare());
            stmt.setLong(2, rezervareVehicul.getIdVehicul());
            stmt.setDate(3, new java.sql.Date(rezervareVehicul.getStartDate().getTime()));
            stmt.setDate(4, new java.sql.Date(rezervareVehicul.getEndDate().getTime()));
            stmt.setLong(5, rezervareVehicul.getIdRezervareVehicul());
            stmt.executeUpdate();
        }
    }

    public void delete(long id) throws SQLException {
        String query = "DELETE FROM RezervareVehicul WHERE idRezervareVehicul = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public RezervareVehicul findByIdWithVehiculAndRezervare(long id) throws SQLException {
        String query = """
        SELECT rv.idRezervareVehicul, rv.startDate, rv.endDate,
               r.idRezervare, r.idClient, r.avans, r.metodaPlata,
               v.idVehicul, v.numarInmatriculare, v.model, v.idAngajat, v.culoare, v.tipVehicul
        FROM RezervareVehicul rv
        LEFT JOIN Rezervare r ON rv.idRezervare = r.idRezervare
        LEFT JOIN Vehicul v ON rv.idVehicul = v.idVehicul
        WHERE rv.idRezervareVehicul = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Rezervare rezervare = null;
                    if (rs.getObject("idRezervare") != null) {
                        rezervare = new Rezervare(
                                rs.getLong("idRezervare"),
                                null, // client null
                                rs.getLong("idClient"),
                                rs.getDouble("avans"),
                                rs.getString("metodaPlata")
                        );
                    }

                    Vehicul vehicul = null;
                    if (rs.getObject("idVehicul") != null) {
                        vehicul = new Vehicul(
                                rs.getLong("idVehicul"),
                                rs.getString("numarInmatriculare"),
                                rs.getString("model"),
                                null, // angajat null
                                rs.getLong("idAngajat"),
                                rs.getString("culoare"),
                                rs.getString("tipVehicul")
                        );
                    }

                    return new RezervareVehicul(
                            rs.getLong("idRezervareVehicul"),
                            rezervare,
                            vehicul,
                            rs.getLong("idRezervare"),
                            rs.getLong("idVehicul"),
                            rs.getDate("startDate"),
                            rs.getDate("endDate")
                    );
                }
            }
        }
        return null;
    }

}
