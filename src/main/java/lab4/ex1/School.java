package src.main.java.lab4.ex1;

public class School {
    private String name;
    private Student[] students;
    private Classroom[] classrooms;
    private Schedule schedule;

    public School(String name, Student[] students, Classroom[] classrooms, String[] subjects) {
        this.name = name;
        this.students = students;
        this.classrooms = classrooms;
        this.schedule = new Schedule(subjects);  // Creăm un obiect Schedule folosind array-ul de subiecte
    }

    public String getName() {
        return name;
    }

    public Student[] getStudents() {
        return students;
    }

    public Classroom[] getClassrooms() {
        return classrooms;
    }

    public Schedule getSchedule() {
        return schedule;
    }
}
