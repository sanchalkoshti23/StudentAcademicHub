
package studenthub.test;

import java.util.List;

import studenthub.dao.SubjectDAO;
import studenthub.model.Subject;

public class SubjectDAOTest {

    public static void main(String[] args) {

        SubjectDAO dao = new SubjectDAO();

        // Test 1: View all subjects
        System.out.println("=== ALL SUBJECTS ===");

        List<Subject> subjects = dao.fetchAllSubjects();

        for (Subject subject : subjects) {
            System.out.println(
                subject.getId() + " | "
                + subject.getName() + " | "
                + subject.getCourse() + " | "
                + subject.getSemester()
            );
        }

        // Test 2: Find a subject
        System.out.println("\n=== FIND SUBJECT ===");

        if (!subjects.isEmpty()) {
            int id = subjects.get(0).getId();

            Subject found = dao.getSubjectById(id);

            if (found != null) {
                System.out.println(
                    "Found: " + found.getName()
                );
            }
        } else {
            System.out.println(
                "No subjects exist yet. Add a test subject first."
            );
        }
    }
}
