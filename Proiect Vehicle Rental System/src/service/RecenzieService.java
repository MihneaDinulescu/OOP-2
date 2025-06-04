package service;

import models.Recenzie;
import repository.RecenzieRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class RecenzieService {
    private static RecenzieService instance;
    private final RecenzieRepository recenzieRepo;

    private RecenzieService(Connection connection) {
        this.recenzieRepo = new RecenzieRepository(connection);
    }

    public static RecenzieService getInstance(Connection connection) {
        if (instance == null) {
            instance = new RecenzieService(connection);
        }
        return instance;
    }

    public void addRecenzie(Recenzie recenzie) throws SQLException {
        recenzieRepo.save(recenzie);
    }

    public Recenzie getRecenzieById(long id) throws SQLException {
        return recenzieRepo.findById(id);
    }

    public Recenzie getRecenzieByIdWithRezervare(long id) throws SQLException {
        return recenzieRepo.findByIdWithRezervare(id);
    }

    public List<Recenzie> getAllRecenzii() throws SQLException {
        return recenzieRepo.findAll();
    }

    public void updateRecenzie(Recenzie recenzie) throws SQLException {
        recenzieRepo.update(recenzie);
    }

    public void deleteRecenzie(long id) throws SQLException {
        recenzieRepo.delete(id);
    }

    public List<Recenzie> getRecenziiNegative() throws SQLException {
        List<Recenzie> toateRecenziile = recenzieRepo.findAll();
        return toateRecenziile.stream()
                .filter(r -> r.getNrStele() <= 2)
                .collect(Collectors.toList());
    }
}
