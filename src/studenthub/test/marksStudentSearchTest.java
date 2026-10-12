package studenthub.test;

import studenthub.dao.MarksDAO;

public class marksStudentSearchTest {

    public static void main(String[] args) {

        MarksDAO dao = new MarksDAO();

        dao.getMarksByStudent(1);

    }
}