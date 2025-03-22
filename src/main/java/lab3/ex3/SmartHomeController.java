package src.main.java.lab3.ex3;

import java.util.ArrayList;
import java.util.List;

class SmartHomeController {
    private static SmartHomeController instance;
    private List<Appliance> appliances;

    private SmartHomeController() {
        appliances = new ArrayList<>();
    }

    public static SmartHomeController getInstance() {
        if (instance == null) {
            instance = new SmartHomeController();
        }
        return instance;
    }

    public void addAppliance(Appliance appliance) {
        appliances.add(appliance);
    }

    public void turnOnAll() {
        System.out.println("\nPornire toate electrocasnicele...");
        for (Appliance appliance : appliances) {
            appliance.turnOn();
        }
    }

    public void turnOffAll() {
        System.out.println("\nOprire toate electrocasnicele...");
        for (Appliance appliance : appliances) {
            appliance.turnOff();
        }
    }

    public int countOnDevices() {
        int count = 0;
        for (Appliance appliance : appliances) {
            if (appliance.isOn()) {
                count++;
            }
        }
        return count;
    }

    public int totalPowerConsumption() {
        int totalPower = 0;
        for (Appliance appliance : appliances) {
            if (appliance.isOn()) {
                totalPower += appliance.getPower();
            }
        }
        return totalPower;
    }

    public void printStatusReport() {
        System.out.println("\n=== Raport de stare a casei inteligente ===");
        for (Appliance appliance : appliances) {
            String status = appliance.isOn() ? "PORNIT" : "OPRIT";
            System.out.println(appliance.getType() + " - " + status + " (Consum: " + appliance.getPower() + "W)");
        }
        System.out.println("Total dispozitive PORNITE: " + countOnDevices());
        System.out.println("Consum total de energie: " + totalPowerConsumption() + "W");
        System.out.println("================================\n");
    }

    public void energyOptimization(int maxValue) {
        System.out.println("\nActivare modul OPTIMIZARE ENERGIE...");

        int currentPowerConsumption = totalPowerConsumption();

        if (currentPowerConsumption > maxValue) {
            appliances.sort((a, b) -> Integer.compare(a.getPriority(), b.getPriority()));

            for (int i = 0; i < appliances.size(); i++) {
                if (currentPowerConsumption <= maxValue) {
                    break;
                }
                Appliance appliance = appliances.get(i);

                if (appliance.isOn()) {
                    appliance.turnOff();
                    currentPowerConsumption -= appliance.getPower();
                }
            }
        }


        printStatusReport();
    }
}
