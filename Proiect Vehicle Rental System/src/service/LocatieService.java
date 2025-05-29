package service;

import exceptions.LocatieNotFoundException;
import models.Angajat;
import models.Locatie;
import repository.LocatieRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class LocatieService {
    private static LocatieService instance;
    private final LocatieRepository locatieRepo;

    private LocatieService(Connection connection) {
        this.locatieRepo = new LocatieRepository(connection);
    }

    public static LocatieService getInstance(Connection connection) {
        if (instance == null) {
            instance = new LocatieService(connection);
        }
        return instance;
    }

    // Adaugă o locație
    public void addLocatie(Locatie locatie) throws SQLException {
        locatieRepo.save(locatie);
    }

    // Obține o locație după ID simplu
    public Locatie getLocatieById(long id) throws SQLException {
        Locatie locatie = locatieRepo.findById(id);
        if (locatie == null) {
            throw new LocatieNotFoundException(id);
        }
        return locatie;
    }

    // Obține o locație după ID cu lista de angajați
    public Locatie getLocatieByIdWithAngajati(long id) throws SQLException {
        Locatie locatie = locatieRepo.findByIdWithAngajati(id);
        if (locatie == null) {
            throw new LocatieNotFoundException(id);
        }
        return locatie;
    }

    // Obține toate locațiile
    public List<Locatie> getAllLocatii() throws SQLException {
        return locatieRepo.findAll();
    }

    // Actualizează o locație
    public void updateLocatie(Locatie locatie) throws SQLException {
        locatieRepo.update(locatie);
    }

    // Șterge o locație
    public void deleteLocatie(long id) throws SQLException {
        locatieRepo.delete(id);
    }

    public List<Locatie> findLocatiiFaraAngajati() throws SQLException {
        return locatieRepo.findLocatiiFaraAngajati();
    }

    public Map<Locatie, Integer> getLocatiiCuNumarRezervari() throws SQLException {
        return locatieRepo.findLocatiiCuNumarRezervari();
    }
}
