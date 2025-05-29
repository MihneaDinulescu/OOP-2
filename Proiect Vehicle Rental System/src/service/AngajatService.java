package service;

import models.Angajat;
import repository.AngajatRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AngajatService {
    private static AngajatService instance;
    private final AngajatRepository angajatRepo;

    // Constructor privat
    private AngajatService(Connection connection) {
        this.angajatRepo = new AngajatRepository(connection);
    }

    // Metodă statică pentru obținerea instanței
    public static AngajatService getInstance(Connection connection) {
        if (instance == null) {
            instance = new AngajatService(connection);
        }
        return instance;
    }

    public void addAngajat(Angajat angajat) throws SQLException {
        angajatRepo.save(angajat);
    }

    public Angajat getAngajatById(long id) throws SQLException {
        return angajatRepo.findById(id);
    }

    public Angajat getAngajatByIdWithLocatieAndVehicule(long id) throws SQLException {
        return angajatRepo.findByIdWithLocatieAndVehicule(id);
    }

    public List<Angajat> getAllAngajati() throws SQLException {
        return angajatRepo.findAll();
    }

    public void updateAngajat(Angajat angajat) throws SQLException {
        angajatRepo.update(angajat);
    }

    public void deleteAngajat(long id) throws SQLException {
        angajatRepo.delete(id);
    }

    public Angajat findAngajatCuCeleMaiMulteVehicule() throws SQLException {
        return angajatRepo.findAngajatCuCeleMaiMulteVehicule();
    }

    public Map<Angajat, Integer> findAllWithNumarVehicule() throws SQLException {
        return angajatRepo.findAllWithNumarVehicule();
    }
}
