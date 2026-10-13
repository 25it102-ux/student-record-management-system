import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * StudentManagementApp - Main Class
 * Console-based Student Record Management System
 * Provides menu-driven interface for CRUD operations
 */
public class StudentManagementApp {
    
    private static Scanner scanner;
    private static StudentService service;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        service = new StudentService();
        
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║     STUDENT RECORD MANAGEMENT SYSTEM                     ║");
        System.out.println("║     Java Console Application - Java 17+                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getValidatedMenuChoice();
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    service.displayAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    System.out.println("\n👋 Thank you for using Student Record Management System!");
                    System.out.println("Exiting application...");
                    running = false;
                    break;
                default:
                    System.out.println("\n❌ Invalid choice! Please select 1-6.");
            }
            
            if (running && choice != 6) {
                System.out.println("\n" + "─".repeat(60));
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                      MAIN MENU                           ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Add Student                                          ║");
        System.out.println("║  2. View All Students                                    ║");
        System.out.println("║  3. Search Student by ID                                 ║");
        System.out.println("║  4. Update Student Details                               ║");
        System.out.println("║  5. Delete Student                                       ║");
        System.out.println("║  6. Exit Application                                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.print("👉 Enter your choice (1-6): ");
    }

    private static int getValidatedMenuChoice() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("\n❌ Invalid input! Please enter a number between 1-6.");
            return 0;
        }
    }

    private static void addStudent() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║              ADD NEW STUDENT                             ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();

        if (service.studentExists(id)) {
            System.out.println("\n❌ ERROR: Student ID '" + id + "' already exists!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();

        int age = getValidatedAge();
        
        System.out.print("Enter Department: ");
        String dept = scanner.nextLine().trim();

        String email = getValidatedEmail();
        
        double cgpa = getValidatedCgpa();

        Student student = new Student(id, name, age, dept, email, cgpa);
        
        String validationError = service.validateStudent(student);
        if (validationError != null) {
            System.out.println("\n" + validationError);
            return;
        }

        service.addStudent(student);
        System.out.println("\n✅ SUCCESS: Student '" + name + "' added successfully!");
    }

    private static int getValidatedAge() {
        while (true) {
            System.out.print("Enter Age (16-40): ");
            try {
                int age = Integer.parseInt(scanner.nextLine().trim());
                if (service.validateAge(age)) {
                    return age;
                } else {
                    System.out.println("❌ Invalid age! Must be between 16 and 40.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a number.");
            }
        }
    }

    private static String getValidatedEmail() {
        while (true) {
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();
            if (service.validateEmail(email)) {
                return email;
            }
            System.out.println("❌ Invalid email format! Example: student@college.edu");
        }
    }

    private static double getValidatedCgpa() {
        while (true) {
            System.out.print("Enter CGPA (0.00-10.00): ");
            try {
                double cgpa = Double.parseDouble(scanner.nextLine().trim());
                if (service.validateCgpa(cgpa)) {
                    return cgpa;
                } else {
                    System.out.println("❌ Invalid CGPA! Must be between 0.00 and 10.00.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a number.");
            }
        }
    }

    private static void searchStudent() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║          SEARCH STUDENT BY ID                            ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();

        Student student = service.findStudentById(id);
        
        if (student == null) {
            System.out.println("\n❌ ERROR: Student with ID '" + id + "' not found!");
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("📋 STUDENT DETAILS:");
        System.out.println("=".repeat(60));
        System.out.printf("Student ID : %s%n", student.getStudentId());
        System.out.printf("Name       : %s%n", student.getName());
        System.out.printf("Age        : %s%n", student.getAge());
        System.out.printf("Department : %s%n", student.getDepartment());
        System.out.printf("Email      : %s%n", student.getEmail());
        System.out.printf("CGPA       : %.2f%n", student.getCgpa());
        System.out.println("=".repeat(60));
    }

    private static void updateStudent() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║          UPDATE STUDENT DETAILS                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        System.out.print("Enter Student ID to update: ");
        String id = scanner.nextLine().trim();

        Student existing = service.findStudentById(id);
        if (existing == null) {
            System.out.println("\n❌ ERROR: Student with ID '" + id + "' not found!");
            return;
        }

        System.out.println("\nCurrent Details:");
        System.out.printf("Name: %s | Age: %s | Dept: %s | Email: %s | CGPA: %.2f%n",
                existing.getName(), existing.getAge(), existing.getDepartment(),
                existing.getEmail(), existing.getCgpa());

        System.out.print("\nEnter New Name: ");
        String name = scanner.nextLine().trim();

        int age = getValidatedAge();
        
        System.out.print("Enter New Department: ");
        String dept = scanner.nextLine().trim();

        String email = getValidatedEmail();
        
        double cgpa = getValidatedCgpa();

        boolean success = service.updateStudent(id, name, age, dept, email, cgpa);
        
        if (success) {
            System.out.println("\n✅ SUCCESS: Student details updated successfully!");
        } else {
            System.out.println("\n❌ ERROR: Failed to update student details.");
        }
    }

    private static void deleteStudent() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║          DELETE STUDENT                                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine().trim();

        Student student = service.findStudentById(id);
        if (student == null) {
            System.out.println("\n❌ ERROR: Student with ID '" + id + "' not found!");
            return;
        }

        System.out.println("\n⚠️  You are about to delete:");
        System.out.printf("Name: %s | Email: %s | CGPA: %.2f%n",
                student.getName(), student.getEmail(), student.getCgpa());
        
        System.out.print("\nAre you sure? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes") || confirm.equals("y")) {
            boolean success = service.deleteStudent(id);
            if (success) {
                System.out.println("\n✅ SUCCESS: Student '" + student.getName() + "' deleted successfully!");
            } else {
                System.out.println("\n❌ ERROR: Failed to delete student.");
            }
        } else {
            System.out.println("\nℹ️  deletion cancelled.");
        }
    }
}