import config.ConnectionProvider;
import model.BankAccount;
import model.Client;
import service.BankService;

public class Main {
    public static void main(String[] args) {
        		ConnectionProvider.getConnection();

        BankService bankService = new BankService();

        String updatedName = "mihnea";
        String updatedAddress = "5fdasasdf";
        boolean updatedActiveStatus = false;

        bankService.updateClient(1L, updatedName, updatedAddress, updatedActiveStatus);

    }
}