package studenthub.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import studenthub.model.Attendance;
import studenthub.util.DBConnection;
import java.sql.PreparedStatement;

import studenthub.model.Attendance;

import studenthub.util.DBConnection;

public class AttendanceDAO {

    // Add attendance
    public void addAttendance(Attendance attendance) {

        String sql = "INSERT INTO attendance "
                   + "(student_id, subject_id, attendance_date, status) "
                   + "VALUES (?, ?, ?, ?)";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, attendance.getStudentId());
            statement.setInt(2, attendance.getSubjectId());
            statement.setString(3, attendance.getAttendanceDate());
            statement.setString(4, attendance.getStatus());

            statement.executeUpdate();

            System.out.println("Attendance added successfully!");

            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println("Failed to add attendance!");
            e.printStackTrace();
        }
    }


    // Get all attendance records
    public void getAllAttendance() {

        String sql = "SELECT * FROM attendance";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            var resultSet = statement.executeQuery();

            System.out.println("----- Attendance List -----");

            while (resultSet.next()) {

                int attendanceId =
                        resultSet.getInt("attendance_id");

                int studentId =
                        resultSet.getInt("student_id");

                int subjectId =
                        resultSet.getInt("subject_id");

                String attendanceDate =
                        resultSet.getString("attendance_date");

                String status =
                        resultSet.getString("status");

                System.out.println(
                        attendanceId + " | "
                        + studentId + " | "
                        + subjectId + " | "
                        + attendanceDate + " | "
                        + status
                );
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println("Failed to get attendance!");
            e.printStackTrace();
        }
    }


    // Get attendance by ID
    public Attendance getAttendanceById(int id) {

        String sql =
                "SELECT * FROM attendance WHERE attendance_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, id);

            var resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Attendance attendance = new Attendance();

                attendance.setAttendanceId(
                        resultSet.getInt("attendance_id"));

                attendance.setStudentId(
                        resultSet.getInt("student_id"));

                attendance.setSubjectId(
                        resultSet.getInt("subject_id"));

                attendance.setAttendanceDate(
                        resultSet.getString("attendance_date"));

                attendance.setStatus(
                        resultSet.getString("status"));

                resultSet.close();
                statement.close();
                connection.close();

                return attendance;
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println("Failed to find attendance!");
            e.printStackTrace();
        }

        return null;
    }
 // Update attendance
    public void updateAttendance(Attendance attendance) {

        String sql = "UPDATE attendance "
                   + "SET student_id = ?, "
                   + "subject_id = ?, "
                   + "attendance_date = ?, "
                   + "status = ? "
                   + "WHERE attendance_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, attendance.getStudentId());
            statement.setInt(2, attendance.getSubjectId());
            statement.setString(3, attendance.getAttendanceDate());
            statement.setString(4, attendance.getStatus());
            statement.setInt(5, attendance.getAttendanceId());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Attendance updated successfully!");
            } else {
                System.out.println("Attendance not found!");
            }

            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println("Failed to update attendance!");
            e.printStackTrace();
        }
    }
 // Delete attendance
    public void deleteAttendance(int id) {

        String sql = "DELETE FROM attendance WHERE attendance_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Attendance deleted successfully!");
            } else {
                System.out.println("Attendance not found!");
            }

            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println("Failed to delete attendance!");
            e.printStackTrace();
        }
    }
 // Count present classes
    public int getPresentCount(int studentId, int subjectId) {

        String sql = "SELECT COUNT(*) FROM attendance "
                   + "WHERE student_id = ? "
                   + "AND subject_id = ? "
                   + "AND status = 'Present'";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, studentId);
            statement.setInt(2, subjectId);

            var resultSet = statement.executeQuery();

            if (resultSet.next()) {

                int presentCount = resultSet.getInt(1);

                resultSet.close();
                statement.close();
                connection.close();

                return presentCount;
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println("Failed to count present classes!");
            e.printStackTrace();
        }

        return 0;
    }
 // Count total classes
    public int getTotalClasses(int studentId, int subjectId) {

        String sql = "SELECT COUNT(*) FROM attendance "
                   + "WHERE student_id = ? "
                   + "AND subject_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, studentId);
            statement.setInt(2, subjectId);

            var resultSet = statement.executeQuery();

            if (resultSet.next()) {

                int totalClasses = resultSet.getInt(1);

                resultSet.close();
                statement.close();
                connection.close();

                return totalClasses;
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println("Failed to count total classes!");
            e.printStackTrace();
        }

        return 0;
    }
 // Calculate attendance percentage
    public double getAttendancePercentage(int studentId, int subjectId) {

        int presentCount = getPresentCount(studentId, subjectId);

        int totalClasses = getTotalClasses(studentId, subjectId);

        if (totalClasses == 0) {
            return 0.0;
        }

        double percentage =
                ((double) presentCount / totalClasses) * 100;

        return percentage;
    }
 // Search Attendance By Student
    public void getAttendanceByStudent(int studentId) {

        String sql = "SELECT attendance_id, student_id, subject_id, "
                   + "attendance_date, status "
                   + "FROM attendance WHERE student_id = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Attendance ID: " + rs.getInt("attendance_id"));
                System.out.println("Student ID: " + rs.getInt("student_id"));
                System.out.println("Subject ID: " + rs.getInt("subject_id"));
                System.out.println("Attendance Date: " + rs.getDate("attendance_date"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("-------------------------");
            }

            if (!found) {
                System.out.println("No attendance records found!");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
  
 // Fetch all attendance records for the GUI
 public List<Attendance> fetchAllAttendance() {

     List<Attendance> records = new ArrayList<>();

     String sql = "SELECT attendance_id, student_id, subject_id, "
                + "attendance_date, status "
                + "FROM attendance ORDER BY attendance_id";

     try (Connection connection = DBConnection.getConnection();
          PreparedStatement statement =
                  connection.prepareStatement(sql);
          ResultSet resultSet = statement.executeQuery()) {

         while (resultSet.next()) {

             Attendance attendance = new Attendance();

             attendance.setAttendanceId(
                     resultSet.getInt("attendance_id"));

             attendance.setStudentId(
                     resultSet.getInt("student_id"));

             attendance.setSubjectId(
                     resultSet.getInt("subject_id"));

             attendance.setAttendanceDate(
                     resultSet.getString("attendance_date"));

             attendance.setStatus(
                     resultSet.getString("status"));

             records.add(attendance);
         }

     } catch (Exception e) {
         System.out.println("Failed to fetch attendance records!");
         e.printStackTrace();
     }

     return records;
 }
//Calculate overall attendance percentage
public double getOverallAttendancePercentage() {
  String sql = "SELECT " +
               "COALESCE(100.0 * SUM(CASE WHEN status = 'Present' " +
               "THEN 1 ELSE 0 END) / NULLIF(COUNT(*), 0), 0) " +
               "FROM attendance";

  try (Connection con = DBConnection.getConnection();
       PreparedStatement ps = con.prepareStatement(sql);
       ResultSet rs = ps.executeQuery()) {

      if (rs.next()) {
          return rs.getDouble(1);
      }

  } catch (Exception e) {
      e.printStackTrace();
  }

  return 0.0;
}

// Get total attendance records for one student
public int getStudentTotalClasses(int studentId) {

    String sql =
            "SELECT COUNT(*) FROM attendance WHERE student_id = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, studentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return 0;
}

// Get present attendance records for one student
public int getStudentPresentClasses(int studentId) {

    String sql =
            "SELECT COUNT(*) FROM attendance "
            + "WHERE student_id = ? AND status = 'Present'";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, studentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return 0;
}

// Calculate attendance percentage for one student
public double getStudentAttendancePercentage(int studentId) {

    int total = getStudentTotalClasses(studentId);
    int present = getStudentPresentClasses(studentId);

    if (total == 0) {
        return 0.0;
    }

    return present * 100.0 / total;
}

}