package src.main.java.lab3.ex3;

public class SmartHomeSimulation {
    public static void main(String[] args) {
        SmartHomeController controller = SmartHomeController.getInstance();

        controller.addAppliance(new Fan(50, 2));
        controller.addAppliance(new Light(10, 1));
        controller.addAppliance(new AC(1500, 3));
        controller.addAppliance(new Heater(2000, 3));
        controller.addAppliance(new Light(15, 1));

        controller.turnOnAll();

        controller.printStatusReport();

        controller.energyOptimization(3520);

        int countOn = controller.countOnDevices();
        System.out.println("Număr de dispozitive PORNITE: " + countOn);

        int totalPower = controller.totalPowerConsumption();
        System.out.println("Consum total de energie: " + totalPower + "W");
    }
}
