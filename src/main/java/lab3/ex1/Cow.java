package src.main.java.lab3.ex1;

class Cow implements Animal {
    private String name;

    public Cow(String name) {
        this.name = name;
    }

    @Override
    public void makeSound() {
        System.out.println("Moo!");
    }

    @Override
    public String getName() {
        return name;
    }
}