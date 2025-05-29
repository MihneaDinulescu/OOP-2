package service;

import models.Vehicul;
import repository.VehiculRepository;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VehiculService {
    private static VehiculService instance;
    private final VehiculRepository vehiculRepo;

    private VehiculService(Connection connection) {
        this.vehiculRepo = new VehiculRepository(connection);
    }

    public static VehiculService getInstance(Connection connection) {
        if (instance == null) {
            instance = new VehiculService(connection);
        }
        return instance;
    }

    public void addVehicul(Vehicul vehicul) throws SQLException {
        vehiculRepo.save(vehicul);
    }

    public Vehicul getVehiculById(long id) throws SQLException {
        return vehiculRepo.findById(id);
    }

    public Vehicul getVehiculByIdWithAngajatAndRezervareVehicul(long id) throws SQLException {
        return vehiculRepo.findByIdWithAngajatAndRezervareVehicul(id);
    }

    public List<Vehicul> getAllVehicule() throws SQLException {
        return vehiculRepo.findAll();
    }

    public void updateVehicul(Vehicul vehicul) throws SQLException {
        vehiculRepo.update(vehicul);
    }

    public void deleteVehicul(long id) throws SQLException {
        vehiculRepo.delete(id);
    }

    public List<Vehicul> getVehiculeDisponibile(Date startDate, Date endDate) throws SQLException {
        List<Vehicul> toateVehiculele = vehiculRepo.findAll();
        List<Vehicul> vehiculeDisponibile = new ArrayList<>();

        for (Vehicul vehicul : toateVehiculele) {
            Vehicul vehiculComplet = vehiculRepo.findByIdWithAngajatAndRezervareVehicul(vehicul.getIdVehicul());

            if (vehiculComplet != null && vehiculComplet.isDisponibil(startDate, endDate)) {
                vehiculeDisponibile.add(vehiculComplet);
            }
        }
        return vehiculeDisponibile;
    }

    public Double getMediaRecenziiPentruVehicul(long idVehicul) throws SQLException {
        return vehiculRepo.getMediaRecenziiPentruVehicul(idVehicul);
    }

    public Map<Vehicul, Double> getTop5VehiculeCuCeaMaiMareMedieRatingMinim5Recenzii() throws SQLException {
        return vehiculRepo.findTop5VehiculeCuCeaMaiMareMedieRatingMinim5Recenzii();
    }

    public List<Vehicul> getVehiculeFaraRezervari() throws SQLException {
        return vehiculRepo.findVehiculeFaraRezervari();
    }


    public Map<String, Integer> getDistributieVehiculePeLocatii() throws SQLException {
        return vehiculRepo.getDistributieVehiculePeLocatii();
    }

}
