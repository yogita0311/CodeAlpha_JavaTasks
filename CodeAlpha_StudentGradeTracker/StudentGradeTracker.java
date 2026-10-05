import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private String name;
    private double grade;

    // Constructor
    public Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }
}

public class StudentGradeTracker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.println("=== Student Grade Tracker ===");

        // Input number of students
        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        // Input student names and grades
        for (int i = 0; i < n; i++) {
            System.out.print("Enter student name: ");
            String name = sc.nextLine();
            
            double grade;
            while(true) {
            System.out.print("Enter grade for " + name + ": ");
            grade = sc.nextDouble();
            sc.nextLine(); // consume newline
            
                if(grade >= 0 && grade <= 100) {
                	break;
                }
                System.out.println("Invalid grade! Please enter a grade between 0 and 100.");
            }
            students.add(new Student(name, grade));
        }

        // Calculate stats
        double sum = 0, highest = Double.MIN_VALUE, lowest = Double.MAX_VALUE;
        String topStudent = "", bottomStudent = "";

        for (Student s : students) {
            double g = s.getGrade();
            sum += g;
            if (g > highest) {
                highest = g;
                topStudent = s.getName();
            }
            if (g < lowest) {
                lowest = g;
                bottomStudent = s.getName();
            }
        }

        double average = sum / students.size();

        // Display Summary Report
        System.out.println("\n========== Grade Report ==========");
        System.out.println("+----------------------+--------+");
        System.out.println("| Student Name         | Grade  |");
        System.out.println("+----------------------+--------+");

        for (Student s : students) {
            System.out.printf("| %-20s | %6.2f |%n",
                    s.getName(), s.getGrade());
        }

        System.out.println("+----------------------+--------+");

        System.out.printf("\nAverage Score: %.2f%n", average);
        System.out.printf("Highest Score: %.2f (%s)%n", highest, topStudent);
        System.out.printf("Lowest Score: %.2f (%s)%n", lowest, bottomStudent);

        sc.close();
    }
}

