package src.main.java.lab3.ex3;

class AC extends Appliance {
    public AC(int power, int priority) {
        super(power, priority);
    }

    @Override
    public String getType() {
        return "Aer Condiționat";
    }
}