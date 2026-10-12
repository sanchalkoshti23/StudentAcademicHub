package studenthub.test;

import studenthub.dao.AttendanceDAO;

public class attendanceTest {

    public static void main(String[] args) {

        AttendanceDAO dao = new AttendanceDAO();

        // Student ID = 1
        // Subject ID = 2

        double percentage =
                dao.getAttendancePercentage(1, 2);

        System.out.println(
                "Attendance Percentage: "
                + percentage + "%"
        );
    }
}