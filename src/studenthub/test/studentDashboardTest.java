package studenthub.test;

import studenthub.dao.StudentDAO;

public class studentDashboardTest {

    public static void main(String[] args) {

        StudentDAO dao = new StudentDAO();

        dao.getStudentDashboard(1);

    }
}