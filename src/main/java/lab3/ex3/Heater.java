package src.main.java.lab3.ex3;

class Heater extends Appliance {
    public Heater(int power, int priority) {
        super(power, priority);
    }

    @Override
    public String getType() {
        return "Încălzitor";
    }
}