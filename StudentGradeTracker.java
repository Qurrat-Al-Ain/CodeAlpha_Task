import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> grades = new ArrayList<>();
        String input;

        System.out.println("=== Student Grade Tracker ===");

        // Collect grades from the teacher
        while (true) {
            System.out.print("Enter a student's grade (or type 'done' to finish): ");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("done")) {
                break;
            }

            try {
                double grade = Double.parseDouble(input);
                if (grade < 0 || grade > 100) {
                    System.out.println("Please enter a grade between 0 and 100.");
                } else {
                    grades.add(grade);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Check if any grades were entered
        if (grades.isEmpty()) {
            System.out.println("No grades entered.");
        } else {
            double sum = 0;
            double highest = grades.get(0);
            double lowest = grades.get(0);

            for (double grade : grades) {

                sum += grade;
                if (grade > highest) highest = grade;
                if (grade < lowest) lowest = grade;
            }

            double average = sum / grades.size();

            System.out.println("\n=== Grade Summary ===");
            System.out.printf("Total Students: %d%n", grades.size());
            System.out.printf("Average Grade: %.2f%n", average);
            System.out.printf("Highest Grade: %.2f%n", highest);
            System.out.printf("Lowest Grade: %.2f%n", lowest);
        }

        scanner.close();
    }
}

