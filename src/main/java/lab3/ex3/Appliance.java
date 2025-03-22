package src.main.java.lab3.ex3;

abstract class Appliance implements Switchable {
    protected int power;
    protected boolean status;
    protected int priority; // Prioritatea dispozitivului (1 = mai puțin important, 3 = mai important)

    public Appliance(int power, int priority) {
        this.power = power;
        this.status = false;
        this.priority = priority;
    }

    public void turnOn() {
        if (!status) {
            status = true;
            System.out.println(getType() + " a fost PORNIT.");
        }
    }

    public void turnOff() {
        if (status) {
            status = false;
            System.out.println(getType() + " a fost OPRIT.");
        }
    }

    public boolean isOn() {
        return status;
    }

    public void toggle() {
        if (status) {
            turnOff();
        } else {
            turnOn();
        }
    }

    public int getPower() {
        return power;
    }

    public abstract String getType();

    public int getPriority() {
        return priority;
    }
}
