package src.main.java.lab4.ex2;

public abstract class Report {
    protected StringBuilder builder;

    // Constructor
    public Report() {
        this.builder = new StringBuilder();
    }

    // Abstract method to generate report data
    public abstract void generate(Object[]... args);

    // Print the report
    public void print() {
        System.out.println(builder.toString());
    }
}
