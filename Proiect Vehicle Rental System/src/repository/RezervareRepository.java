package repository;

import interfaces.IRepository;
import models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RezervareRepository implements IRepository<Rezervare> {
    private Connection connection;

    public RezervareRepository(Connection connection) {
        this.connection = connection;
    }

    // Adaugă o rezervare
    public void save(Rezervare rezervare) throws SQLException {
        String query = "INSERT INTO Rezervare (idRezervare, idClient, avans, metodaPlata) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, rezervare.getIdRezervare());
            stmt.setLong(2, rezervare.getIdClient());
            stmt.setDouble(3, rezervare.getAvans());
            stmt.setString(4, rezervare.getMetodaPlata());
            stmt.executeUpdate();
        }
    }

    // Căutare rezervare după ID
    public Rezervare findById(long id) throws SQLException {
        String query = "SELECT * FROM Rezervare WHERE idRezervare = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new Rezervare(
                            resultSet.getLong("idRezervare"),
                            null,
                            resultSet.getLong("idClient"),
                            resultSet.getDouble("avans"),
                            resultSet.getString("metodaPlata")
                    );
                }
            }
        }
        return null;
    }

    // Obține toate rezervările
    public List<Rezervare> findAll() throws SQLException {
        List<Rezervare> rezervari = new ArrayList<>();
        String query = "SELECT * FROM Rezervare";
        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                rezervari.add(new Rezervare(
                        resultSet.getLong("idRezervare"),
                        null,
                        resultSet.getLong("idClient"),
                        resultSet.getDouble("avans"),
                        resultSet.getString("metodaPlata")
                ));
            }
        }
        return rezervari;
    }

    // Actualizează o rezervare
    public void update(Rezervare rezervare) throws SQLException {
        String query = "UPDATE Rezervare SET idClient = ?, avans = ?, metodaPlata = ? WHERE idRezervare = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, rezervare.getIdClient());
            stmt.setDouble(2, rezervare.getAvans());
            stmt.setString(3, rezervare.getMetodaPlata());
            stmt.setLong(4, rezervare.getIdRezervare());
            stmt.executeUpdate();
        }
    }

    // Șterge o rezervare
    public void delete(long id) throws SQLException {
        String query = "DELETE FROM Rezervare WHERE idRezervare = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Rezervare findByIdWithRecenziiRezervareVehiculAndClient(long id) throws SQLException {
        String query = """
        SELECT rez.idRezervare, rez.idClient, rez.avans, rez.metodaPlata,
               c.idClient AS c_idClient, c.nume, c.prenume, c.numarTelefon, c.email,
               r.idRecenzie, r.nrStele, r.dataRecenzie,
               rv.idRezervareVehicul, rv.idRezervare AS rvIdRezervare, rv.idVehicul AS rvIdVehicul, rv.startDate, rv.endDate
        FROM Rezervare rez
        LEFT JOIN Client c ON rez.idClient = c.idClient
        LEFT JOIN Recenzie r ON rez.idRezervare = r.idRezervare
        LEFT JOIN RezervareVehicul rv ON rez.idRezervare = rv.idRezervare
        WHERE rez.idRezervare = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Rezervare rezervare = null;
                List<Recenzie> recenzii = new ArrayList<>();
                List<RezervareVehicul> rezervariVehicul = new ArrayList<>();
                boolean rezervareInit = false;

                while (rs.next()) {
                    if (!rezervareInit) {
                        Client client = null;
                        long idClient = rs.getLong("c_idClient");
                        if (!rs.wasNull()) {
                            client = new Client(
                                    idClient,
                                    rs.getString("nume"),
                                    rs.getString("prenume"),
                                    rs.getString("numarTelefon"),
                                    rs.getString("email")
                            );
                        }

                        rezervare = new Rezervare(
                                rs.getLong("idRezervare"),
                                client,
                                idClient,
                                rs.getDouble("avans"),
                                rs.getString("metodaPlata")
                        );

                        rezervareInit = true;
                    }

                    long idRecenzie = rs.getLong("idRecenzie");
                    if (!rs.wasNull() && idRecenzie != 0) {
                        Recenzie recenzie = new Recenzie(
                                idRecenzie,
                                rs.getInt("nrStele"),
                                rs.getDate("dataRecenzie"),
                                null
                        );
                        recenzii.add(recenzie);
                    }

                    long idRezervareVehicul = rs.getLong("idRezervareVehicul");
                    if (!rs.wasNull() && idRezervareVehicul != 0) {
                        RezervareVehicul rv = new RezervareVehicul(
                                idRezervareVehicul,
                                null,
                                null,
                                rs.getLong("rvIdRezervare"),
                                rs.getLong("rvIdVehicul"),
                                rs.getDate("startDate"),
                                rs.getDate("endDate")
                        );
                        rezervariVehicul.add(rv);
                    }
                }

                if (rezervare != null) {
                    rezervare.setRecenzii(recenzii);
                    rezervare.setRezervariVehicul(rezervariVehicul);
                }
                return rezervare;
            }
        }
    }
    public double calculeazaTotalPlataPerRezervare(long idRezervare) throws SQLException {
        String query = """
        SELECT r.avans, rv.startDate, rv.endDate, rv.idVehicul
        FROM Rezervare r
        LEFT JOIN RezervareVehicul rv ON r.idRezervare = rv.idRezervare
        WHERE r.idRezervare = ?
    """;

        VehiculRepository vehiculRepo = new VehiculRepository(connection);

        double sumaPreturiVehicule = 0.0;
        double avans = 0.0;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, idRezervare);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;

                    if (avans == 0.0) {
                        avans = rs.getDouble("avans");
                    }

                    long idVehicul = rs.getLong("idVehicul");
                    Date startDate = rs.getDate("startDate");
                    Date endDate = rs.getDate("endDate");

                    if (startDate == null || endDate == null) continue;

                    int zile = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;

                    Vehicul vehicul = vehiculRepo.findByIdWithCopy(idVehicul);
                    if (vehicul == null) continue;

                    sumaPreturiVehicule += vehicul.calculPretInchiriere(zile);
                }

                if (!found) return 0.0;

                double restDePlata = sumaPreturiVehicule - avans;
                return Math.max(restDePlata, 0);
            }
        }
    }

    public Map<String, Integer> getNumarRezervariLunare() throws SQLException {
        String query = """
        SELECT 
            TO_CHAR(rv.startDate, 'YYYY-MM') AS luna,
            COUNT(*) AS numarRezervari
        FROM RezervareVehicul rv
        GROUP BY TO_CHAR(rv.startDate, 'YYYY-MM')
        ORDER BY luna
    """;

        Map<String, Integer> rezultat = new LinkedHashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String luna = rs.getString("luna");
                int numar = rs.getInt("numarRezervari");

                rezultat.put(luna, numar);
            }
        }

        return rezultat;
    }

}
