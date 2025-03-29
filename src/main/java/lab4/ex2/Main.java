package src.main.java.lab4.ex2;

import src.main.java.lab4.ex1.School;
import src.main.java.lab4.ex1.Student;
import src.main.java.lab4.ex1.Classroom;

public class Main {
    public static void main(String[] args) {

        // Crearea obiectelor Student
        Student student1 = new Student("Alice", 12);
        Student student2 = new Student("Bob", 13);

        Student[] students = {student1, student2};

        // Crearea obiectelor Classroom
        Classroom classroom1 = new Classroom("Classroom 1", 30);
        Classroom classroom2 = new Classroom("Classroom 2", 30);

        Classroom[] classrooms = {classroom1, classroom2};

        // Crearea obiectului Schedule (subiectele)
        String[] subjects = {"Math", "Science", "English", "History"};

        // Crearea obiectului School
        School school = new School("Green Valley School", students, classrooms, subjects);

        // Crearea și generarea StudentReport
        Report studentReport = new StudentReport();
        studentReport.generate(new Object[] {school.getStudents(), school.getClassrooms(), school.getSchedule().getSubjects()});
        studentReport.print();

        // Crearea și generarea SchoolSummaryReport
        Report schoolSummaryReport = new SchoolSummaryReport();
        schoolSummaryReport.generate(new Object[] {school.getStudents(), school.getClassrooms(), school.getSchedule().getSubjects()});
        schoolSummaryReport.print();
    }
}
