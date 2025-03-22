package src.main.java.lab3.ex1;

class Cat implements Animal {
    private String name;

    public Cat(String name) {
        this.name = name;
    }

    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }

    @Override
    public String getName() {
        return name;
    }
}
