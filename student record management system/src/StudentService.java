import java.util.ArrayList;
import java.util.regex.Pattern;

public class StudentService {
    
    private ArrayList<Student> students;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final int MIN_AGE = 16;
    private static final int MAX_AGE = 40;
    private static final double MIN_CGPA = 0.0;
    private static final double MAX_CGPA = 10.0;

    public StudentService() {
        this.students = new ArrayList<>();
    }

    public boolean addStudent(Student student) {
        if (studentExists(student.getStudentId())) return false;
        students.add(student);
        return true;
    }

    public boolean studentExists(String studentId) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(studentId)) return true;
        }
        return false;
    }

    public Student findStudentById(String studentId) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(studentId)) return s;
        }
        return null;
    }

    public boolean updateStudent(String studentId, String name, int age, 
                                  String department, String email, double cgpa) {
        Student student = findStudentById(studentId);
        if (student == null) return false;
        student.setName(name);
        student.setAge(age);
        student.setDepartment(department);
        student.setEmail(email);
        student.setCgpa(cgpa);
        return true;
    }

    public boolean deleteStudent(String studentId) {
        Student student = findStudentById(studentId);
        if (student == null) return false;
        students.remove(student);
        return true;
    }

    public ArrayList<Student> getAllStudents() { return students; }
    public int getStudentCount() { return students.size(); }
    public boolean validateAge(int age) { return age >= MIN_AGE && age <= MAX_AGE; }
    public boolean validateCgpa(double cgpa) { return cgpa >= MIN_CGPA && cgpa <= MAX_CGPA; }
    public boolean validateEmail(String email) {
        return email != null && !email.isEmpty() && EMAIL_PATTERN.matcher(email).matches();
    }

    public String validateStudent(Student student) {
        if (studentExists(student.getStudentId()))
            return "ERROR: Student ID '" + student.getStudentId() + "' already exists!";
        if (!validateAge(student.getAge()))
            return "ERROR: Age must be between " + MIN_AGE + " and " + MAX_AGE + "!";
        if (!validateCgpa(student.getCgpa()))
            return "ERROR: CGPA must be between " + MIN_CGPA + " and " + MAX_CGPA + "!";
        if (!validateEmail(student.getEmail()))
            return "ERROR: Invalid email format!";
        return null;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("\n⚠️  No student records found!");
            return;
        }
        System.out.println("\n" + "=".repeat(100));
        System.out.printf("%-12s %-20s %-6s %-15s %-25s %-8s%n", 
                "STUDENT ID", "NAME", "AGE", "DEPARTMENT", "EMAIL", "CGPA");
        System.out.println("=".repeat(100));
        for (Student s : students) {
            System.out.println(s.toTableString());
        }
        System.out.println("=".repeat(100));
        System.out.printf("Total Students: %d%n", students.size());
    }
}