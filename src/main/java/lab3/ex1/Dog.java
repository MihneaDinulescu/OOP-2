package src.main.java.lab3.ex1;

class Dog implements Animal {
    private String name;

    public Dog(String name) {
        this.name = name;
    }

    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }

    @Override
    public String getName() {
        return name;
    }
}
