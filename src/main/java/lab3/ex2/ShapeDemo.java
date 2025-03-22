package src.main.java.lab3.ex2;

public class ShapeDemo {
    public static void main(String[] args) {
        Shape[] shapes = {
                new Circle("Roșu", 5),
                new Rectangle("Albastru", 4, 6),
                new Triangle("Verde", 3, 8),
                new Circle("Roșu", 3),
                new Rectangle("Galben", 2, 5)
        };

        Shape.countShapes(shapes);

        Shape.findLargestShape(shapes);

        Shape.searchByColor(shapes, "Roșu");
    }
}
