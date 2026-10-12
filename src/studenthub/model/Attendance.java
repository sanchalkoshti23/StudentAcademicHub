package studenthub.model;

public class Attendance {

    private int attendanceId;
    private int studentId;
    private int subjectId;
    private String attendanceDate;
    private String status;

    // Default constructor
    public Attendance() {
    }

    // Parameterized constructor
    public Attendance(int attendanceId, int studentId, int subjectId,
                      String attendanceDate, String status) {

        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    // Attendance ID
    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    // Student ID
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    // Subject ID
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    // Attendance Date
    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    // Status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}