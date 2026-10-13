/**
 * Student Model Class
 * Represents a student with all required fields
 */
public class Student {
    // Private fields for encapsulation
    private String studentId;
    private String name;
    private int age;
    private String department;
    private String email;
    private double cgpa;

    /**
     * Constructor with all parameters
     */
    public Student(String studentId, String name, int age, String department, 
                   String email, double cgpa) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.department = department;
        this.email = email;
        this.cgpa = cgpa;
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getCgpa() { return cgpa; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }

    /**
     * Returns formatted string for table display
     */
    public String toTableString() {
        return String.format("%-12s %-20s %-6s %-15s %-25s %-8.2f",
                studentId, name, age, department, email, cgpa);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                ", cgpa=" + cgpa +
                '}';
    }
}