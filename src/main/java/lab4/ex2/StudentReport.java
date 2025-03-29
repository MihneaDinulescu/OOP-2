package src.main.java.lab4.ex2;

import src.main.java.lab4.ex1.Student;
import src.main.java.lab4.ex1.Classroom;

public class StudentReport extends Report {

    @Override
    public void generate(Object[]... args) {
        for (Object[] arg : args) {
            if (arg[0] instanceof Student[] && arg[1] instanceof Classroom[] && arg[2] instanceof String[]) {
                Student[] students = (Student[]) arg[0];
                builder.append("Student Report\n");
                builder.append("==============\n");

                for (Student student : students) {
                    builder.append("Name: ").append(student.name()).append("\n")
                            .append("Age: ").append(student.age()).append("\n")
                            .append("---------------------------\n");
                }
            }
        }
    }
}
