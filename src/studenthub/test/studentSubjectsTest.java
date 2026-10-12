package studenthub.test;

import studenthub.dao.StudentSubjectDAO;
import studenthub.model.StudentSubject;

public class studentSubjectsTest {

    public static void main(String[] args) {
    	StudentSubjectDAO dao = new StudentSubjectDAO();

    	System.out.println("Restoring enrollment...");

    	StudentSubject enrollment = new StudentSubject(1, 2);

    	dao.enrollStudent(enrollment);

    	System.out.println("\nChecking enrolled subjects:");

    	dao.getSubjectsByStudent(1);
    }
}