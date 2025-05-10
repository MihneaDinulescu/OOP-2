package model;

public class BankAccount {
    private long accountId;
    private long clientId;
    private String iban;
    private double balance;
    private String currency;
    private boolean isActive;

    public BankAccount(long accountId, long clientId, String iban, double balance, String currency, boolean isActive) {
        this.accountId = accountId;
        this.clientId = clientId;
        this.iban = iban;
        this.balance = balance;
        this.currency = currency;
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountId=" + accountId +
                ", clientId=" + clientId +
                ", iban='" + iban + '\'' +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    public long getAccountId() {
        return accountId;
    }

    public long getClientId() {
        return clientId;
    }

    public double getBalance() {
        return balance;
    }

    public String getIban() {
        return iban;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isActive() {
        return isActive;
    }
}