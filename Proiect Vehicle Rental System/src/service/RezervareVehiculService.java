package service;

import models.RezervareVehicul;
import repository.RezervareVehiculRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RezervareVehiculService {
    private static RezervareVehiculService instance;
    private final RezervareVehiculRepository rezervareVehiculRepo;

    private RezervareVehiculService(Connection connection) {
        this.rezervareVehiculRepo = new RezervareVehiculRepository(connection);
    }

    public static RezervareVehiculService getInstance(Connection connection) {
        if (instance == null) {
            instance = new RezervareVehiculService(connection);
        }
        return instance;
    }

    // Adaugă o rezervare vehicul
    public void addRezervareVehicul(RezervareVehicul rezervareVehicul) throws SQLException {
        rezervareVehiculRepo.save(rezervareVehicul);
    }

    // Obține o rezervare vehicul după ID simplu
    public RezervareVehicul getRezervareVehiculById(long id) throws SQLException {
        return rezervareVehiculRepo.findById(id);
    }

    // Obține o rezervare vehicul după ID cu vehicul și rezervare detaliată
    public RezervareVehicul getRezervareVehiculByIdWithVehiculAndRezervare(long id) throws SQLException {
        return rezervareVehiculRepo.findByIdWithVehiculAndRezervare(id);
    }

    // Obține toate rezervările pentru vehicule
    public List<RezervareVehicul> getAllRezervariVehicul() throws SQLException {
        return rezervareVehiculRepo.findAll();
    }

    // Actualizează o rezervare vehicul
    public void updateRezervareVehicul(RezervareVehicul rezervareVehicul) throws SQLException {
        rezervareVehiculRepo.update(rezervareVehicul);
    }

    // Șterge o rezervare vehicul
    public void deleteRezervareVehicul(long id) throws SQLException {
        rezervareVehiculRepo.delete(id);
    }

}
