package src.main.java.lab3.ex3;

class Fan extends Appliance {
    public Fan(int power, int priority) {
        super(power, priority);
    }

    @Override
    public String getType() {
        return "Ventilator";
    }
}
