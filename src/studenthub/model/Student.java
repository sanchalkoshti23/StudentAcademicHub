
package studenthub.model;

public class Student {

    private int studentId;
    private String name;
    private String email;
    private String college;
    private String branch;
    private int year;

    // Default constructor
    public Student() {

    }

    // Parameterized constructor
    public Student(int studentId, String name, String email,
                   String college, String branch, int year) {

        this.studentId = studentId;
        setName(name);
        setEmail(email);
        setCollege(college);
        setBranch(branch);
        setYear(year);
    }

    // Getter and setter for studentId
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Student name cannot be empty.");
        }

        this.name = name.trim();
    }

    // Getter and setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null ||
            !email.trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

            throw new IllegalArgumentException(
                    "Please enter a valid email address.");
        }

        this.email = email.trim();
    }

    // Getter and setter for college
    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        if (college == null || college.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "College name cannot be empty.");
        }

        this.college = college.trim();
    }

    // Getter and setter for branch
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        if (branch == null || branch.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Branch cannot be empty.");
        }

        this.branch = branch.trim();
    }

    // Getter and setter for year
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1 || year > 4) {
            throw new IllegalArgumentException(
                    "Student year must be between 1 and 4.");
        }

        this.year = year;
    }
    @Override
    public String toString() {
        return getStudentId() + " - " + getName();
    }
}
