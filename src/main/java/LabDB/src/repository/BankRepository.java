package repository;

import model.BankAccount;
import model.Client;

import java.sql.*;
import java.util.Optional;

public class BankRepository {

    private static BankRepository instance;

    private BankRepository() {
    }

    public static BankRepository getInstance() {
        if (instance == null) {
            instance = new BankRepository();
        }
        return instance;
    }


    public void insertData(Connection connection, Client client) {
        String sql = """
            INSERT INTO bank_clients (id, name, address, active_status)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, client.getId());
            ps.setString(2, client.getName());
            ps.setString(3, client.getAddress());
            ps.setBoolean(4, client.isActiveStatus());

            int rows = ps.executeUpdate();
            System.out.println(rows + " row(s) inserted into bank_clients.");
        } catch (SQLException e) {
            throw new RuntimeException("Insert client failed: " + e.getMessage(), e);
        }
    }


    public Optional<Client> getClient(Connection connection, long clientId) {
        String sql = "SELECT * FROM bank_clients WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Client(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getBoolean("active_status")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Get client failed: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void updateClient(Connection connection, long clientId, String name, String address, boolean activeStatus) {
        String sql = """
        UPDATE bank_clients
        SET name = ?, address = ?, active_status = ?
        WHERE id = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setBoolean(3, activeStatus);
            ps.setLong(4, clientId);

            int rows = ps.executeUpdate();
            System.out.println(rows + " row(s) updated in bank_clients.");
        } catch (SQLException e) {
            throw new RuntimeException("Update client failed: " + e.getMessage(), e);
        }
    }


    public void deleteClient(Connection connection, long clientId) {
        String sql = "DELETE FROM bank_clients WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, clientId);
            int rows = ps.executeUpdate();
            System.out.println(rows + " row(s) deleted from bank_clients.");
        } catch (SQLException e) {
            throw new RuntimeException("Delete client failed: " + e.getMessage(), e);
        }
    }


    public void insertAccount(Connection connection, BankAccount account) {
        String sql = """
            INSERT INTO bank_accounts (account_id, client_id, iban, balance, currency, is_active)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, account.getAccountId());
            ps.setLong(2, account.getClientId());
            ps.setString(3, account.getIban());
            ps.setDouble(4, account.getBalance());
            ps.setString(5, account.getCurrency());
            ps.setBoolean(6, account.isActive());

            int rows = ps.executeUpdate();
            System.out.println(rows + " row(s) inserted into bank_accounts.");
        } catch (SQLException e) {
            throw new RuntimeException("Insert bank account failed: " + e.getMessage(), e);
        }
    }


    public Optional<BankAccount> getAccount(Connection connection, long accountId) {
        String sql = "SELECT * FROM bank_accounts WHERE account_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new BankAccount(
                            rs.getLong("account_id"),
                            rs.getLong("client_id"),
                            rs.getString("iban"),
                            rs.getDouble("balance"),
                            rs.getString("currency"),
                            rs.getBoolean("is_active")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Get bank account failed: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void updateAccount(Connection connection, long accountId, long clientId, String iban,
                              double balance, String currency, boolean isActive) {
        String sql = """
            UPDATE bank_accounts
            SET client_id = ?, iban = ?, balance = ?, currency = ?, is_active = ?
            WHERE account_id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, clientId);
            ps.setString(2, iban);
            ps.setDouble(3, balance);
            ps.setString(4, currency);
            ps.setBoolean(5, isActive);
            ps.setLong(6, accountId);

            int rows = ps.executeUpdate();
            System.out.println(rows + " row(s) updated in bank_accounts.");
        } catch (SQLException e) {
            throw new RuntimeException("Update bank account failed: " + e.getMessage(), e);
        }
    }

    public void deleteAccount(Connection connection, long accountId) {
        String sql = "DELETE FROM bank_accounts WHERE account_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, accountId);
            int rows = ps.executeUpdate();
            System.out.println(rows + " row(s) deleted from bank_accounts.");
        } catch (SQLException e) {
            throw new RuntimeException("Delete bank account failed: " + e.getMessage(), e);
        }
    }

    public void cleanUp(Connection connection) {
        String sqlDeleteAccounts = "DELETE FROM bank_accounts";
        String sqlDeleteClients = "DELETE FROM bank_clients";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sqlDeleteAccounts);
            stmt.executeUpdate(sqlDeleteClients);

            System.out.println("All records deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Clean up failed: " + e.getMessage(), e);
        }
    }
}

