package studenthub.test;

import studenthub.dao.AttendanceDAO;

public class attendanceStudentSearchTest {

    public static void main(String[] args) {

        AttendanceDAO dao = new AttendanceDAO();

        dao.getAttendanceByStudent(1);

    }
}