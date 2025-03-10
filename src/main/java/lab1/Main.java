package src.main.java.lab1;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        User user1 = new User("Ion Popescu", 'M', "1234567890123", new ContCurent(1000, "RO49AAAA1B31007593840000"));
        User user2 = new User("Maria Ionescu", 'F', "9876543210987", new ContEconomii(1500, "RO49BBBB1B31007593840001"));
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Alegeți opțiunea: ");
            System.out.println("1. Depunere");
            System.out.println("2. Retragere");
            System.out.println("3. Verificare sold");
            System.out.println("Introduceti 'exit' sau 'quit' pentru a opri programul.");
            String optiune = scanner.nextLine();

            switch (optiune) {
                case "1":
                    System.out.println("Introduceți suma pentru depunere:");
                    double sumaDepunere = scanner.nextDouble();
                    scanner.nextLine();
                    user1.depune(sumaDepunere);
                    break;
                case "2":
                    System.out.println("Introduceți suma pentru retragere:");
                    double sumaRetragere = scanner.nextDouble();
                    scanner.nextLine();
                    user1.retrage(sumaRetragere);
                    break;
                case "3":
                    user1.verificaSold();
                    break;
                case "exit":
                case "quit":
                    running = false;
                    System.out.println("Ieșire din aplicație.");
                    break;
                default:
                    System.out.println("Opțiune invalidă.");
            }
        }

        scanner.close();
    }
}
