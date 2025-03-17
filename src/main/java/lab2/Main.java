package src.main.java.lab2;

public class Main {
    public static void main(String[] args) {
        Departament IT = new Departament("IT");
        Departament HR = new Departament("HR");

        Departamente.adaugaDepartament(IT);
        Departamente.adaugaDepartament(HR);

        ManagementService.getInstance().inregistreazaAngajat("Ion", "Popescu", 2020, 3000, IT);
        ManagementService.getInstance().inregistreazaAngajat("Alex", "Ionescu", 2021, 3500, IT);

        ManagementService.getInstance().inregistreazaAngajat("Maria", "Georgescu", 2021, 4000, HR);
        ManagementService.getInstance().inregistreazaAngajat("Elena", "Dumitru", 2022, 4500, HR);

        System.out.println("Salariile medii din departamentele companiei:");
        ManagementService.getInstance().afiseazaSalariuMediuDinDepartament(IT);
        ManagementService.getInstance().afiseazaSalariuMediuDinDepartament(HR);

        Angajat angajatIon = IT.getMembri().get(0);
        ManagementService.getInstance().schimbaDepartament(angajatIon, HR);

        Angajat angajatAlex = IT.getMembri().get(0);
        ManagementService.getInstance().schimbaSalariu(angajatAlex, 3800);
        
        System.out.println("\nDupă modificările efectuate:");
        ManagementService.getInstance().afiseazaSalariuMediuDinDepartament(IT);
        ManagementService.getInstance().afiseazaSalariuMediuDinDepartament(HR);
    }
}
