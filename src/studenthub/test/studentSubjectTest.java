package studenthub.test;

import studenthub.dao.StudentSubjectDAO;
import studenthub.model.StudentSubject;

public class studentSubjectTest {

    public static void main(String[] args) {

        StudentSubjectDAO dao = new StudentSubjectDAO();

        StudentSubject enrollment = new StudentSubject();

        // Student ID
        enrollment.setStudentId(1);

        // Subject ID = 2
        enrollment.setSubjectId(2);

        // Enroll student
        dao.enrollStudent(enrollment);
    }
}