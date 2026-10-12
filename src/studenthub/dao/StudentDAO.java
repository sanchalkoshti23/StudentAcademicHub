
package studenthub.dao;

import studenthub.model.Student;
import studenthub.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // 1. Add a new student
    public void addStudent(Student student) {

        String sql = "INSERT INTO students "
                + "(name, email, college, branch, year) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getCollege());
            ps.setString(4, student.getBranch());
            ps.setInt(5, student.getYear());

            ps.executeUpdate();

            System.out.println("Student added successfully.");

        } catch (SQLException e) {
            System.out.println("Error adding student: "
                    + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // 2. Get all students as a list
    public List<Student> getAllStudentsList() {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT student_id, name, email, college, "
                + "branch, year FROM students ORDER BY student_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Student student = new Student();

                student.setStudentId(rs.getInt("student_id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setCollege(rs.getString("college"));
                student.setBranch(rs.getString("branch"));
                student.setYear(rs.getInt("year"));

                students.add(student);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving students: "
                    + e.getMessage());
            throw new RuntimeException(e);
        }

        return students;
    }

    // 3. Display all students in the console
    public void getAllStudents() {

        List<Student> students = getAllStudentsList();

        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        for (Student student : students) {
            System.out.println(
                    "ID: " + student.getStudentId()
                    + " | Name: " + student.getName()
                    + " | Email: " + student.getEmail()
                    + " | College: " + student.getCollege()
                    + " | Branch: " + student.getBranch()
                    + " | Year: " + student.getYear()
            );
        }
    }

    // 4. Find a student by ID
    public Student getStudentById(int studentId) {

        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Student student = new Student();

                    student.setStudentId(
                            rs.getInt("student_id"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    student.setCollege(rs.getString("college"));
                    student.setBranch(rs.getString("branch"));
                    student.setYear(rs.getInt("year"));

                    return student;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding student: "
                    + e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }

    // 5. Update an existing student
    public void updateStudent(Student student) {

        String sql = "UPDATE students SET name = ?, email = ?, "
                + "college = ?, branch = ?, year = ? "
                + "WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getCollege());
            ps.setString(4, student.getBranch());
            ps.setInt(5, student.getYear());
            ps.setInt(6, student.getStudentId());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student updated successfully.");
            } else {
                System.out.println("Student ID not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating student: "
                    + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // 6. Delete a student by ID
    public void deleteStudent(int studentId) {

        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Student ID not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting student: "
                    + e.getMessage());
            throw new RuntimeException(e);
        }
    }
   
 // Student Dashboard
 public void getStudentDashboard(int studentId) {

     Student student = getStudentById(studentId);

     if (student == null) {
         System.out.println("Student not found with ID: " + studentId);
         return;
     }

     System.out.println("\n========== STUDENT DASHBOARD ==========");
     System.out.println("Student ID : " + student.getStudentId());
     System.out.println("Name       : " + student.getName());
     System.out.println("Email      : " + student.getEmail());
     System.out.println("College    : " + student.getCollege());
     System.out.println("Branch     : " + student.getBranch());
     System.out.println("Year       : " + student.getYear());

     String attendanceSQL =
             "SELECT COUNT(*) AS total_classes, " +
             "SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) " +
             "AS present_classes FROM attendance WHERE student_id = ?";

     try (Connection con = DBConnection.getConnection();
          PreparedStatement ps = con.prepareStatement(attendanceSQL)) {

         ps.setInt(1, studentId);

         try (ResultSet rs = ps.executeQuery()) {
             if (rs.next()) {
                 int total = rs.getInt("total_classes");
                 int present = rs.getInt("present_classes");

                 double percentage = total > 0
                         ? (present * 100.0 / total) : 0.0;

                 System.out.println("Total Classes : " + total);
                 System.out.println("Present       : " + present);
                 System.out.printf("Attendance    : %.2f%%%n", percentage);
             }
         }

     } catch (SQLException e) {
         System.out.println("Unable to load attendance: " + e.getMessage());
     }

     String marksSQL =
             "SELECT COUNT(*) AS total_exams, " +
             "AVG(marks_obtained * 100.0 / NULLIF(max_marks, 0)) " +
             "AS average_percentage FROM marks WHERE student_id = ?";

     try (Connection con = DBConnection.getConnection();
          PreparedStatement ps = con.prepareStatement(marksSQL)) {

         ps.setInt(1, studentId);

         try (ResultSet rs = ps.executeQuery()) {
             if (rs.next()) {
                 System.out.println("Exams Recorded : "
                         + rs.getInt("total_exams"));

                 double average = rs.getDouble("average_percentage");

                 if (rs.wasNull()) {
                     System.out.println("Average Marks  : No marks recorded");
                 } else {
                     System.out.printf("Average Marks  : %.2f%%%n", average);
                 }
             }
         }

     } catch (SQLException e) {
         System.out.println("Unable to load marks: " + e.getMessage());
     }

     System.out.println("=======================================");
 }


 // Search students by name
 public void searchStudentByName(String searchName) {

     String sql = "SELECT student_id, name, email, college, branch, year "
             + "FROM students WHERE name LIKE ? ORDER BY student_id";

     try (Connection con = DBConnection.getConnection();
          PreparedStatement ps = con.prepareStatement(sql)) {

         ps.setString(1, "%" + searchName.trim() + "%");

         try (ResultSet rs = ps.executeQuery()) {

             boolean found = false;

             while (rs.next()) {
                 found = true;

                 System.out.println("\nStudent ID : "
                         + rs.getInt("student_id"));
                 System.out.println("Name       : "
                         + rs.getString("name"));
                 System.out.println("Email      : "
                         + rs.getString("email"));
                 System.out.println("College    : "
                         + rs.getString("college"));
                 System.out.println("Branch     : "
                         + rs.getString("branch"));
                 System.out.println("Year       : "
                         + rs.getInt("year"));
                 System.out.println("------------------------------");
             }

             if (!found) {
                 System.out.println(
                         "No students found matching: " + searchName);
             }

         }

     } catch (SQLException e) {
         System.out.println("Error searching students: " + e.getMessage());
     }
 }
//Get the total number of students
public int getTotalStudents() {
  String sql = "SELECT COUNT(*) FROM students";

  try (java.sql.Connection con =
               studenthub.util.DBConnection.getConnection();
       java.sql.PreparedStatement ps = con.prepareStatement(sql);
       java.sql.ResultSet rs = ps.executeQuery()) {

      if (rs.next()) {
          return rs.getInt(1);
      }

  } catch (Exception e) {
      e.printStackTrace();
  }

  return 0;
}
//Get the total number of subjects
public int getTotalSubjects() {
 String sql = "SELECT COUNT(*) FROM subjects";

 try (java.sql.Connection con =
              studenthub.util.DBConnection.getConnection();
      java.sql.PreparedStatement ps = con.prepareStatement(sql);
      java.sql.ResultSet rs = ps.executeQuery()) {

     if (rs.next()) {
         return rs.getInt(1);
     }

 } catch (Exception e) {
     e.printStackTrace();
 }

 return 0;
}

// Search students by name, email, branch, or college
public List<Student> searchStudents(String keyword) {

    List<Student> students = new ArrayList<>();

    String sql = "SELECT student_id, name, email, college, "
               + "branch, year FROM students "
               + "WHERE name LIKE ? "
               + "OR email LIKE ? "
               + "OR branch LIKE ? "
               + "OR college LIKE ? "
               + "ORDER BY student_id";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        String searchTerm = "%" + keyword.trim() + "%";

        ps.setString(1, searchTerm);
        ps.setString(2, searchTerm);
        ps.setString(3, searchTerm);
        ps.setString(4, searchTerm);

        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Student student = new Student();

                student.setStudentId(rs.getInt("student_id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setCollege(rs.getString("college"));
                student.setBranch(rs.getString("branch"));
                student.setYear(rs.getInt("year"));

                students.add(student);
            }
        }

    } catch (SQLException e) {
        System.out.println("Error searching students: "
                + e.getMessage());
        throw new RuntimeException(e);
    }

    return students;
    
}

// Filter students by branch and academic year
// Use "All" to ignore a filter.
public List<Student> filterStudents(String branch, int year) {

    List<Student> students = new ArrayList<>();

    StringBuilder sql = new StringBuilder(
            "SELECT student_id, name, email, college, branch, year "
            + "FROM students WHERE 1 = 1"
    );

    if (branch != null && !branch.equals("All")) {
        sql.append(" AND branch = ?");
    }

    if (year != 0) {
        sql.append(" AND year = ?");
    }

    sql.append(" ORDER BY student_id");

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql.toString())) {

        int parameterIndex = 1;

        if (branch != null && !branch.equals("All")) {
            ps.setString(parameterIndex++, branch);
        }

        if (year != 0) {
            ps.setInt(parameterIndex, year);
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student student = new Student();

                student.setStudentId(rs.getInt("student_id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setCollege(rs.getString("college"));
                student.setBranch(rs.getString("branch"));
                student.setYear(rs.getInt("year"));

                students.add(student);
            }
        }

    } catch (SQLException e) {
        System.out.println("Error filtering students: "
                + e.getMessage());
        throw new RuntimeException(e);
    }

    return students;
}

}
