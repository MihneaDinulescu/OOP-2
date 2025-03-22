package src.main.java.lab3.ex1;

public class Main {
    public static void main(String[] args) {
        Animal[] animals = {
                new Dog("Rex"),
                new Cat("Bella"),
                new Cow("Paula")
        };

        for (int i = 0; i < animals.length; i++) {
            System.out.print((i + 1) + ". " + animals[i].getClass().getSimpleName() +
                    " (" + animals[i].getName() + "): ");
            animals[i].makeSound();
        }
    }
}
