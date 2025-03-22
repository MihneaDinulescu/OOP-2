package src.main.java.lab3.ex2;

abstract class Shape {
    protected String color;

    public Shape(String color) {
        this.color = color;
    }

    public abstract double getArea();
    public abstract String getType();

    public String getColor() {
        return color;
    }

    public static void countShapes(Shape[] shapes) {
        int circles = 0, rectangles = 0, triangles = 0;
        for (Shape shape : shapes) {
            switch (shape.getType()) {
                case "Cerc": circles++; break;
                case "Dreptunghi": rectangles++; break;
                case "Triunghi": triangles++; break;
            }
        }
        System.out.println("Cercuri: " + circles + ", Dreptunghiuri: " + rectangles + ", Triunghiuri: " + triangles);
    }

    public static void findLargestShape(Shape[] shapes) {
        if (shapes.length == 0) {
            System.out.println("Nu există forme în array.");
            return;
        }
        Shape largest = shapes[0];
        for (Shape shape : shapes) {
            if (shape.getArea() > largest.getArea()) {
                largest = shape;
            }
        }
        System.out.println("Cea mai mare formă: " + largest.getType() + " cu aria " + largest.getArea());
    }

    public static void searchByColor(Shape[] shapes, String color) {
        boolean found = false;
        for (Shape shape : shapes) {
            if (shape.getColor().equalsIgnoreCase(color)) {
                System.out.println("S-a găsit " + shape.getType() + " cu culoarea " + color);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nu s-a găsit nicio formă cu culoarea " + color);
        }
    }
}
