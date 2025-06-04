package repository;

import interfaces.IRepository;
import models.Recenzie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecenzieRepository implements IRepository<Recenzie> {
    private Connection connection;

    public RecenzieRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Recenzie recenzie) throws SQLException {
        String query = "INSERT INTO Recenzie (idRecenzie, nrStele, dataRecenzie, idRezervare) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, recenzie.getIdRecenzie());
            stmt.setInt(2, recenzie.getNrStele());
            stmt.setDate(3, new java.sql.Date(recenzie.getDataRecenzie().getTime()));
            stmt.setLong(4, recenzie.getRezervare().getIdRezervare());
            stmt.executeUpdate();
        }
    }

    public Recenzie findById(long id) throws SQLException {
        String query = "SELECT * FROM Recenzie WHERE idRecenzie = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new Recenzie(
                            resultSet.getLong("idRecenzie"),
                            resultSet.getInt("nrStele"),
                            resultSet.getDate("dataRecenzie"),
                            null
                    );
                }
            }
        }
        return null;
    }

    public List<Recenzie> findAll() throws SQLException {
        List<Recenzie> recenzii = new ArrayList<>();
        String query = "SELECT * FROM Recenzie";
        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                recenzii.add(new Recenzie(
                        resultSet.getLong("idRecenzie"),
                        resultSet.getInt("nrStele"),
                        resultSet.getDate("dataRecenzie"),
                        null
                ));
            }
        }
        return recenzii;
    }

    public void update(Recenzie recenzie) throws SQLException {
        String query = "UPDATE Recenzie SET nrStele = ?, dataRecenzie = ?, idRezervare = ? WHERE idRecenzie = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, recenzie.getNrStele());
            stmt.setDate(2, new java.sql.Date(recenzie.getDataRecenzie().getTime()));
            stmt.setLong(3, recenzie.getRezervare().getIdRezervare());
            stmt.setLong(4, recenzie.getIdRecenzie());
            stmt.executeUpdate();
        }
    }

    public void delete(long id) throws SQLException {
        String query = "DELETE FROM Recenzie WHERE idRecenzie = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Recenzie findByIdWithRezervare(long id) throws SQLException {
        String query = """
        SELECT r.idRecenzie, r.nrStele, r.dataRecenzie,
               rez.idRezervare, rez.idClient, rez.avans, rez.metodaPlata
        FROM Recenzie r
        LEFT JOIN Rezervare rez ON r.idRezervare = rez.idRezervare
        WHERE r.idRecenzie = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    models.Rezervare rezervare = null;
                    long idRezervare = rs.getLong("idRezervare");
                    if (!rs.wasNull()) {
                        rezervare = new models.Rezervare(
                                idRezervare,
                                null,  // client null
                                rs.getLong("idClient"),
                                rs.getDouble("avans"),
                                rs.getString("metodaPlata")
                        );
                    }

                    return new Recenzie(
                            rs.getLong("idRecenzie"),
                            rs.getInt("nrStele"),
                            rs.getDate("dataRecenzie"),
                            rezervare
                    );
                }
            }
        }
        return null;
    }

}
