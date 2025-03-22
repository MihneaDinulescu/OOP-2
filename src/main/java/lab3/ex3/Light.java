package src.main.java.lab3.ex3;

class Light extends Appliance {
    public Light(int power, int priority) {
        super(power, priority);
    }

    @Override
    public String getType() {
        return "Lumină";
    }
}