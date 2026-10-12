
package studenthub.test;

import studenthub.dao.StudentDAO;
import studenthub.model.Student;

public class studentDAOValidationTest {

    public static void main(String[] args) {

        StudentDAO dao = new StudentDAO();

        // Invalid name: should be rejected before database insertion
        Student student = new Student();
        student.setEmail("test@gmail.com");
        student.setCollege("KIT College");
        student.setBranch("CSE");
        student.setYear(3);

        // Name remains null, so the DAO should reject this object
        dao.addStudent(student);

        System.out.println("Validation test completed.");
    }
}
