package service;

import exceptions.ClientNotFoundException;
import models.Client;
import repository.ClientRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class ClientService {
    private static ClientService instance;
    private final ClientRepository clientRepo;

    // Constructor privat pentru Singleton
    private ClientService(Connection connection) {
        this.clientRepo = new ClientRepository(connection);
    }

    // Metodă statică pentru acces global
    public static ClientService getInstance(Connection connection) {
        if (instance == null) {
            instance = new ClientService(connection);
        }
        return instance;
    }

    public void addClient(Client client) throws SQLException {
        clientRepo.save(client);
    }

    public Client getClientById(long id) throws SQLException {
        Client client = clientRepo.findById(id);
        if (client == null) {
            throw new ClientNotFoundException(id);
        }
        return client;
    }

    public Client getClientByIdWithRezervari(long id) throws SQLException {
        Client client = clientRepo.findByIdWithRezervari(id);
        if (client == null) {
            throw new ClientNotFoundException(id);
        }
        return client;
    }


    public List<Client> getAllClients() throws SQLException {
        return clientRepo.findAll();
    }

    public void updateClient(Client client) throws SQLException {
        clientRepo.update(client);
    }

    public void deleteClient(long id) throws SQLException {
        clientRepo.delete(id);
    }

    public List<Client> getAllClientsSortedByNameJava() throws SQLException {
        List<Client> clienti = clientRepo.findAll();
        clienti.sort(Comparator.comparing(Client::getNume));
        return clienti;
    }

    public List<Client> getTop3ClientiCuCeleMaiMulteRezervari() throws SQLException {
        return clientRepo.findTop3ByRezervari();
    }

    public HashMap<Client, Integer> getClientiCuNumarRezervari() throws SQLException {
        List<Client> allClients = clientRepo.findAll();
        int numberOfClients = allClients.size();

        HashMap<Client, Integer> map = new HashMap<>();
        for (int i = 0; i < numberOfClients; i++) {
            long id = allClients.get(i).getIdClient();
            Client client = clientRepo.findByIdWithRezervari(id);
            if (client != null) {
                int nrRezervari = (client.getRezervari() != null) ? client.getRezervari().size() : 0;
                map.put(client, nrRezervari);
            }
        }
        return map;
    }

    public Map<Client, Double> getClientsWithMaxAvansMap() throws SQLException {
        return clientRepo.findClientsWithMaxAvansMap();
    }


}
