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

    public void addRezervare(Rezervare rezervare) throws SQLException {
        rezervareRepo.save(rezervare);
    }

    public Rezervare getRezervareById(long id) throws SQLException {
        return rezervareRepo.findById(id);
    }

    public Rezervare getRezervareByIdWithRecenziiRezervareVehiculAndClient(long id) throws SQLException {
        return rezervareRepo.findByIdWithRecenziiRezervareVehiculAndClient(id);
    }

    public List<Rezervare> getAllRezervari() throws SQLException {
        return rezervareRepo.findAll();
    }

    public void updateRezervare(Rezervare rezervare) throws SQLException {
        rezervareRepo.update(rezervare);
    }

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
