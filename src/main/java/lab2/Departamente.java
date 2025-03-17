package src.main.java.lab2;

import java.util.ArrayList;
import java.util.List;

class Departamente {
    private static List<Departament> departamente = new ArrayList<>();

    public static void adaugaDepartament(Departament departament) {
        departamente.add(departament);
    }

    public static void afiseazaDepartamente() {
        for (Departament departament : departamente) {
            System.out.println(departament.getNumeDepartament());
        }
    }

    public static List<Departament> getDepartamente() {
        return departamente;
    }
}
