package studenthub.test;

import studenthub.dao.SubjectDAO;

public class subjectTest {

    public static void main(String[] args) {

        SubjectDAO dao = new SubjectDAO();

        // Delete subject with ID 1
        dao.deleteSubject(1);

        // Display remaining subjects
        dao.getAllSubjects();
    }
}