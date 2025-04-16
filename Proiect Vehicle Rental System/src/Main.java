import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ServiceManager serviceManager = ServiceManager.getInstance();

        Locatie locatie = new Locatie(1, "România", "București", "Str. Exemplu", 10);
        serviceManager.adaugaLocatie(locatie);

        Angajat angajat1 = new Angajat(1, "Ion", "Popescu", "0751234567", "ion.popescu@example.com", "Manager", locatie);
        serviceManager.adaugaAngajat(angajat1);

        Client client1 = new Client(1, "Maria", "Ionescu", "0741234567", "maria.ionescu@example.com");
        Client client2 = new Client(2, "Andrei", "Popescu", "0723456789", "andrei.popescu@example.com");
        Client client3 = new Client(3, "Elena", "Marin", "0734567890", "elena.marin@example.com");
        Client client4 = new Client(4, "George", "Dumitrescu", "0755678901", "george.dumitrescu@example.com");

        serviceManager.adaugaClient(client1);
        serviceManager.adaugaClient(client2);
        serviceManager.adaugaClient(client3);
        serviceManager.adaugaClient(client4);


        Bicicleta bicicleta = new Bicicleta(1, "BIC123", "MountainBike", angajat1, "Albastru");
        Camion camion = new Camion(2, "CAM123", "Volvo", angajat1, "Gri");
        Masina masina = new Masina(3, "MAS123", "BMW", angajat1, "Negru");

        serviceManager.adaugaVehicul(bicicleta);
        serviceManager.adaugaVehicul(camion);
        serviceManager.adaugaVehicul(masina);

        Rezervare rezervare = serviceManager.creeazaRezervare(client1, 200, "Card");

        Date dataStart = new Date(2025 - 1900, 3, 8); // 8 aprilie 2025
        Date dataEnd = new Date(2025 - 1900, 3, 15);  // 15 aprilie 2025

        try {
            Vehicul vehiculDisponibil = serviceManager.cautaVehiculDisponibil(dataStart, dataEnd);
            serviceManager.adaugaVehiculLaRezervare(rezervare, vehiculDisponibil, dataStart, dataEnd);
            System.out.println("Vehiculul rezervat: " + vehiculDisponibil.getModel() + " pentru clientul " + client1.getNume());
        } catch (VehiculIndisponibilException e) {
            System.out.println("Eroare: " + e.getMessage());
        }

        serviceManager.efectueazaPlata(rezervare);

        serviceManager.adaugaRecenzieLaRezervare(rezervare, 5, new Date());

        serviceManager.afiseazaDetaliiRezervare(rezervare);

        serviceManager.sorteazaSiAfiseazaClienti();

    }
}
