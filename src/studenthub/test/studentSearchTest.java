package studenthub.test;

import studenthub.dao.StudentDAO;

public class studentSearchTest {

    public static void main(String[] args) {

        StudentDAO dao = new StudentDAO();

        dao.searchStudentByName("Sanchal");

    }
}