package service;

import config.ConnectionProvider;
import model.BankAccount;
import model.Client;
import repository.BankRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class BankService {

    private final BankRepository bankRepository = BankRepository.getInstance();

    public BankService() {}

    public void insertClient(Client client) {
        try (Connection connection = ConnectionProvider.getConnection()) {
            bankRepository.insertData(connection, client);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert client", e);
        }
    }

    public Client getClient(long clientId) {
        try (Connection connection = ConnectionProvider.getConnection()) {
            Optional<Client> result = bankRepository.getClient(connection, clientId);
            if (result.isPresent()) {
                return result.get();
            } else {
                throw new RuntimeException("Client not found with ID: " + clientId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve client", e);
        }
    }

    public void updateClient(long clientId, String name, String address, boolean activeStatus) {
        try (Connection connection = ConnectionProvider.getConnection()) {
            bankRepository.updateClient(connection, clientId, name, address, activeStatus);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update client", e);
        }
    }


    public void deleteClient(long clientId) {
        try (Connection connection = ConnectionProvider.getConnection()) {
            bankRepository.deleteClient(connection, clientId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete client", e);
        }
    }

    public void insertBankAccount(BankAccount bankAccount) {
        try (Connection connection = ConnectionProvider.getConnection()) {
            bankRepository.insertAccount(connection, bankAccount);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert bank account", e);
        }
    }


    public BankAccount getBankAccount(long accountId) {
        try (Connection connection = ConnectionProvider.getConnection()) {
            Optional<BankAccount> result = bankRepository.getAccount(connection, accountId);
            if (result.isPresent()) {
                return result.get();
            } else {
                throw new RuntimeException("Bank account not found with ID: " + accountId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve bank account", e);
        }
    }

    public void updateBankAccount(long accountId, long clientId, String iban, double balance, String currency, boolean isActive) {
        try (Connection connection = ConnectionProvider.getConnection()) {
            bankRepository.updateAccount(connection, accountId, clientId, iban, balance, currency, isActive);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update bank account", e);
        }
    }

    public void deleteBankAccount(long accountId) {
        try (Connection connection = ConnectionProvider.getConnection()) {
            bankRepository.deleteAccount(connection, accountId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete bank account", e);
        }
    }

    public void cleanUp() {
        try (Connection connection = ConnectionProvider.getConnection()) {
            bankRepository.cleanUp(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clean up database", e);
        }
    }
}
