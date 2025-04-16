import java.util.*;


public class ServiceManager {
    private static ServiceManager instance;
    private List<Vehicul> vehicule;
    private List<Locatie> locatii;
    private List<Angajat> angajati;
    private List<Client> clienti;
    private List<Rezervare> rezervari;

    private ServiceManager() {
        vehicule = new ArrayList<>();
        locatii = new ArrayList<>();
        angajati = new ArrayList<>();
        clienti = new ArrayList<>();
        rezervari = new ArrayList<>();
    }

    public static ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }

    public void adaugaVehicul(Vehicul vehicul) {
        vehicule.add(vehicul);
    }

    public List<Vehicul> getVehicule() {
        return vehicule;
    }

    public Vehicul cautaVehiculDisponibil(Date dataStart, Date dataEnd) throws VehiculIndisponibilException {
        for (Vehicul vehicul : vehicule) {
            if (vehicul.isDisponibil(dataStart, dataEnd)) {
                return vehicul;
            }
        }
        throw new VehiculIndisponibilException("Nu există vehicul disponibil în această perioadă.");
    }

    public void adaugaLocatie(Locatie locatie) {
        locatii.add(locatie);
    }

    public void adaugaAngajat(Angajat angajat) {
        angajati.add(angajat);
    }

    public void adaugaClient(Client client) {
        clienti.add(client);
    }

    public void adaugaRezervare(Rezervare rezervare) {
        rezervari.add(rezervare);
    }

    public Rezervare creeazaRezervare(Client client, double avans, String metodaPlata) {
        long idRezervare = rezervari.size() + 1;
        Rezervare rezervare = new Rezervare(idRezervare, client, avans, metodaPlata);
        adaugaRezervare(rezervare);
        client.adaugaRezervare(rezervare);
        return rezervare;
    }

    public void adaugaVehiculLaRezervare(Rezervare rezervare, Vehicul vehicul, Date startDate, Date endDate) {
        long idRezervareVehicul = rezervare.getRezervareVehicule().size() + 1;
        RezervareVehicul rezervareVehicul = new RezervareVehicul(idRezervareVehicul, rezervare, vehicul, startDate, endDate);
        rezervare.adaugaRezervareVehicul(rezervareVehicul);
        vehicul.getRezervariVehicul().add(rezervareVehicul);
    }

    public void adaugaRecenzieLaRezervare(Rezervare rezervare, int nrStele, Date dataRecenzie) {
        long idRecenzie = rezervare.getRecenzii().size() + 1;  
        Recenzie recenzie = new Recenzie(idRecenzie, nrStele, dataRecenzie, rezervare);
        rezervare.adaugaRecenzie(recenzie);
    }

    public List<Recenzie> getRecenziiRezervare(Rezervare rezervare) {
        return rezervare.getRecenzii();
    }

    public void efectueazaPlata(Rezervare rezervare) {
        double totalDePlata = rezervare.getAvans();  // Exemplu simplificat, doar avansul
        System.out.println("Plata avansului de " + totalDePlata + " a fost efectuată pentru rezervare #" + rezervare.getIdRezervare());
    }

    public void afiseazaDetaliiRezervare(Rezervare rezervare) {
        System.out.println("Detalii rezervare pentru clientul " + rezervare.getClient().getNume() + " " + rezervare.getClient().getPrenume());
        System.out.println("Avans platit: " + rezervare.getAvans());
        for (RezervareVehicul rv : rezervare.getRezervareVehicule()) {
            System.out.println("Vehicul: " + rv.getVehicul().getModel() + " | Perioada: " + rv.getStartDate() + " - " + rv.getEndDate());
        }
        for (Recenzie recenzie : rezervare.getRecenzii()) {
            System.out.println("Recenzie: " + recenzie.getNrStele() + " stele, Data: " + recenzie.getDataRecenzie());
        }
    }

    public void sorteazaSiAfiseazaClienti() {
        Client[] clientiArray = clienti.toArray(new Client[0]);

        Arrays.sort(clientiArray, new Comparator<Client>() {
            @Override
            public int compare(Client c1, Client c2) {
                int numeComp = c1.getNume().compareToIgnoreCase(c2.getNume());
                if (numeComp != 0) {
                    return numeComp;
                }
                return c1.getPrenume().compareToIgnoreCase(c2.getPrenume());
            }
        });

        System.out.println("Clienți sortați alfabetic:");
        for (Client client : clientiArray) {
            System.out.println(client.getNume() + " " + client.getPrenume());
        }
    }

}

