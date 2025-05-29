package service;

import models.Rezervare;
import repository.RezervareRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RezervareService {
    private static RezervareService instance;
    private final RezervareRepository rezervareRepo;

    private RezervareService(Connection connection) {
        this.rezervareRepo = new RezervareRepository(connection);
    }

    public static RezervareService getInstance(Connection connection) {
        if (instance == null) {
            instance = new RezervareService(connection);
        }
        return instance;
    }

    // Adaugă o rezervare
    public void addRezervare(Rezervare rezervare) throws SQLException {
        rezervareRepo.save(rezervare);
    }

    // Obține o rezervare după ID simplu
    public Rezervare getRezervareById(long id) throws SQLException {
        return rezervareRepo.findById(id);
    }

    // Obține o rezervare după ID cu lista de recenzii, rezervareVehicul și client
    public Rezervare getRezervareByIdWithRecenziiRezervareVehiculAndClient(long id) throws SQLException {
        return rezervareRepo.findByIdWithRecenziiRezervareVehiculAndClient(id);
    }

    // Obține toate rezervările
    public List<Rezervare> getAllRezervari() throws SQLException {
        return rezervareRepo.findAll();
    }

    // Actualizează o rezervare
    public void updateRezervare(Rezervare rezervare) throws SQLException {
        rezervareRepo.update(rezervare);
    }

    // Șterge o rezervare
    public void deleteRezervare(long id) throws SQLException {
        rezervareRepo.delete(id);
    }

    public double calculeazaTotalPlataPerRezervare(long idRezervare) throws SQLException {
        return rezervareRepo.calculeazaTotalPlataPerRezervare(idRezervare);
    }

    public Map<String, Integer> getNumarRezervariLunare() throws SQLException {
        return rezervareRepo.getNumarRezervariLunare();
    }

}
