package studenthub.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import studenthub.model.StudentSubject;
import studenthub.util.DBConnection;
import java.sql.PreparedStatement;

import studenthub.model.StudentSubject;
import studenthub.util.DBConnection;

public class StudentSubjectDAO {

    // Enroll a student in a subject

	// Enroll a student in a subject
	public void enrollStudent(StudentSubject studentSubject) {

	    String sql = "INSERT INTO student_subjects (student_id, subject_id) VALUES (?, ?)";

	    try (Connection connection = DBConnection.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {

	        statement.setInt(1, studentSubject.getStudentId());
	        statement.setInt(2, studentSubject.getSubjectId());

	        statement.executeUpdate();

	        System.out.println("Student enrolled in subject successfully!");

	    } catch (java.sql.SQLIntegrityConstraintViolationException e) {

	        System.out.println(
	            "This student is already enrolled in this subject, "
	            + "or a referenced student/subject does not exist."
	        );

	    } catch (Exception e) {

	        System.out.println("Failed to enroll student!");
	        e.printStackTrace();
	    }
	}
	
 // Search Students By Subject
    public void getStudentsBySubject(int subjectId) {

        String sql = "SELECT s.student_id, s.name, s.email, "
                   + "s.college, s.branch, s.year "
                   + "FROM students s "
                   + "JOIN student_subjects ss "
                   + "ON s.student_id = ss.student_id "
                   + "WHERE ss.subject_id = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, subjectId);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Student ID: " + rs.getInt("student_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("College: " + rs.getString("college"));
                System.out.println("Branch: " + rs.getString("branch"));
                System.out.println("Year: " + rs.getInt("year"));
                System.out.println("-------------------------");
            }

            if (!found) {
                System.out.println("No students found for this subject!");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
 // Search Notes By Subject
    public void getNotesBySubject(int subjectId) {

        String sql = "SELECT note_id, subject_id, title, description, file_path "
                   + "FROM notes WHERE subject_id = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, subjectId);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Note ID: " + rs.getInt("note_id"));
                System.out.println("Subject ID: " + rs.getInt("subject_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("File Path: " + rs.getString("file_path"));
                System.out.println("-------------------------");
            }

            if (!found) {
                System.out.println("No notes found for this subject!");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
 // Get Subjects By Student
    public void getSubjectsByStudent(int studentId) {

        String sql = "SELECT sub.subject_id, sub.subject_name, "
                   + "sub.subject_code, sub.semester "
                   + "FROM subjects sub "
                   + "JOIN student_subjects ss "
                   + "ON sub.subject_id = ss.subject_id "
                   + "WHERE ss.student_id = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Subject ID: " + rs.getInt("subject_id"));
                System.out.println("Subject Name: " + rs.getString("subject_name"));
                System.out.println("Subject Code: " + rs.getString("subject_code"));
                System.out.println("Semester: " + rs.getInt("semester"));
                System.out.println("-------------------------");
            }

            if (!found) {
                System.out.println("No subjects found for this student!");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
 // Remove a student's subject enrollment
    public void removeEnrollment(int studentId, int subjectId) {

        String sql = "DELETE FROM student_subjects "
                   + "WHERE student_id = ? AND subject_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Enrollment removed successfully!");
            } else {
                System.out.println("Enrollment not found!");
            }

        } catch (Exception e) {
            System.out.println("Failed to remove enrollment!");
            e.printStackTrace();
        }
    }
  
 // Fetch all student-subject enrollments for the GUI
 public List<Object[]> fetchAllEnrollments() {

     List<Object[]> enrollments = new ArrayList<>();

     String sql = "SELECT ss.student_id, s.name, "
                + "ss.subject_id, sub.subject_name, sub.subject_code "
                + "FROM student_subjects ss "
                + "JOIN students s ON ss.student_id = s.student_id "
                + "JOIN subjects sub ON ss.subject_id = sub.subject_id "
                + "ORDER BY ss.student_id, ss.subject_id";

     try (Connection con = DBConnection.getConnection();
          PreparedStatement ps = con.prepareStatement(sql);
          ResultSet rs = ps.executeQuery()) {

         while (rs.next()) {

             Object[] row = {
                 rs.getInt("student_id"),
                 rs.getString("name"),
                 rs.getInt("subject_id"),
                 rs.getString("subject_name"),
                 rs.getString("subject_code")
             };

             enrollments.add(row);
         }

     } catch (Exception e) {
         System.out.println("Failed to fetch enrollments!");
         e.printStackTrace();
     }

     return enrollments;
 }
 
    
}