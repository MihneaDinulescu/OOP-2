package src.main.java.lab4.ex2;

import src.main.java.lab4.ex1.Classroom;
import src.main.java.lab4.ex1.Student;

public class SchoolSummaryReport extends Report {

    @Override
    public void generate(Object[]... args) {
        for (Object[] arg : args) {
            if (arg[0] instanceof Student[] && arg[1] instanceof Classroom[] && arg[2] instanceof String[]) {
                Student[] students = (Student[]) arg[0];
                Classroom[] classrooms = (Classroom[]) arg[1];
                String[] subjects = (String[]) arg[2];

                builder.append("School Summary Report\n");
                builder.append("======================\n");
                builder.append("Number of Students: ").append(students.length).append("\n");

                builder.append("Classrooms:\n");
                for (Classroom classroom : classrooms) {
                    builder.append("- ").append(classroom.getName()).append(", Capacity: ").append(classroom.getCapacity()).append("\n");
                }

                builder.append("Schedule:\n");
                for (String subject : subjects) {
                    builder.append("- ").append(subject).append("\n");
                }
            }
        }
    }
}
