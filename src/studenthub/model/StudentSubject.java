package studenthub.model;

public class StudentSubject {

    private int studentId;
    private int subjectId;

    // Default constructor
    public StudentSubject() {
    }

    // Parameterized constructor
    public StudentSubject(int studentId, int subjectId) {
        this.studentId = studentId;
        this.subjectId = subjectId;
    }

    // Getter for student ID
    public int getStudentId() {
        return studentId;
    }

    // Setter for student ID
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    // Getter for subject ID
    public int getSubjectId() {
        return subjectId;
    }

    // Setter for subject ID
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}