package studenthub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import studenthub.model.Marks;
import studenthub.util.DBConnection;

public class MarksDAO {

    // Add marks
    public void addMarks(Marks marks) {

        String sql = "INSERT INTO marks "
                   + "(student_id, subject_id, exam_type, marks_obtained, max_marks) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, marks.getStudentId());
            statement.setInt(2, marks.getSubjectId());
            statement.setString(3, marks.getExamType());
            statement.setDouble(4, marks.getMarksObtained());
            statement.setDouble(5, marks.getMaxMarks());

            statement.executeUpdate();

            System.out.println("Marks added successfully!");

            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println("Failed to add marks!");
            e.printStackTrace();
        }
    }


    // Get all marks
    public void getAllMarks() {

        String sql = "SELECT * FROM marks";

        try {

            Connection connection = DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            System.out.println("----- Marks List -----");

            while (resultSet.next()) {

                int markId =
                        resultSet.getInt("mark_id");

                int studentId =
                        resultSet.getInt("student_id");

                int subjectId =
                        resultSet.getInt("subject_id");

                String examType =
                        resultSet.getString("exam_type");

                double marksObtained =
                        resultSet.getDouble("marks_obtained");

                double maxMarks =
                        resultSet.getDouble("max_marks");

                System.out.println(
                        markId + " | "
                        + studentId + " | "
                        + subjectId + " | "
                        + examType + " | "
                        + marksObtained + " | "
                        + maxMarks
                );
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println("Failed to get marks!");
            e.printStackTrace();
        }
    }


    // Get marks by ID
    public Marks getMarksById(int id) {

        String sql =
                "SELECT * FROM marks WHERE mark_id = ?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, id);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                Marks marks = new Marks();

                marks.setMarkId(
                        resultSet.getInt("mark_id"));

                marks.setStudentId(
                        resultSet.getInt("student_id"));

                marks.setSubjectId(
                        resultSet.getInt("subject_id"));

                marks.setExamType(
                        resultSet.getString("exam_type"));

                marks.setMarksObtained(
                        resultSet.getDouble("marks_obtained"));

                marks.setMaxMarks(
                        resultSet.getDouble("max_marks"));

                resultSet.close();
                statement.close();
                connection.close();

                return marks;
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println("Failed to find marks!");
            e.printStackTrace();
        }

        return null;
    }


    // Update marks
    public void updateMarks(Marks marks) {

        String sql = "UPDATE marks "
                   + "SET student_id = ?, "
                   + "subject_id = ?, "
                   + "exam_type = ?, "
                   + "marks_obtained = ?, "
                   + "max_marks = ? "
                   + "WHERE mark_id = ?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, marks.getStudentId());
            statement.setInt(2, marks.getSubjectId());
            statement.setString(3, marks.getExamType());
            statement.setDouble(4, marks.getMarksObtained());
            statement.setDouble(5, marks.getMaxMarks());
            statement.setInt(6, marks.getMarkId());

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated > 0) {

                System.out.println(
                        "Marks updated successfully!");

            } else {

                System.out.println(
                        "Marks not found!");
            }

            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println(
                    "Failed to update marks!");

            e.printStackTrace();
        }
    }


    // Delete marks
    public void deleteMarks(int id) {

        String sql =
                "DELETE FROM marks WHERE mark_id = ?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, id);

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted > 0) {

                System.out.println(
                        "Marks deleted successfully!");

            } else {

                System.out.println(
                        "Marks not found!");
            }

            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println(
                    "Failed to delete marks!");

            e.printStackTrace();
        }
    }


    // Calculate marks percentage
    public double getPercentage(double marksObtained,
                                double maxMarks) {

        if (maxMarks == 0) {

            return 0.0;
        }

        double percentage =
                (marksObtained / maxMarks) * 100;

        return percentage;
    }
 // Calculate grade
    public String getGrade(double percentage) {

        if (percentage >= 90) {

            return "A+";

        } else if (percentage >= 80) {

            return "A";

        } else if (percentage >= 70) {

            return "B";

        } else if (percentage >= 60) {

            return "C";

        } else if (percentage >= 50) {

            return "D";

        } else {

            return "F";
        }
    }
 // Search Marks By Student
    public void getMarksByStudent(int studentId) {

        String sql = "SELECT mark_id, student_id, subject_id, "
                   + "exam_type, marks_obtained, max_marks "
                   + "FROM marks WHERE student_id = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Mark ID: " + rs.getInt("mark_id"));
                System.out.println("Student ID: " + rs.getInt("student_id"));
                System.out.println("Subject ID: " + rs.getInt("subject_id"));
                System.out.println("Exam Type: " + rs.getString("exam_type"));
                System.out.println("Marks Obtained: " + rs.getDouble("marks_obtained"));
                System.out.println("Maximum Marks: " + rs.getDouble("max_marks"));
                System.out.println("-------------------------");
            }

            if (!found) {
                System.out.println("No marks found for this student!");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
 // Get all marks as a list for GUI
    public java.util.List<Marks> getAllMarksList() {

        java.util.List<Marks> marksList = new java.util.ArrayList<>();

        String sql = "SELECT * FROM marks ORDER BY mark_id";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {

                Marks marks = new Marks();

                marks.setMarkId(resultSet.getInt("mark_id"));
                marks.setStudentId(resultSet.getInt("student_id"));
                marks.setSubjectId(resultSet.getInt("subject_id"));
                marks.setExamType(resultSet.getString("exam_type"));
                marks.setMarksObtained(resultSet.getDouble("marks_obtained"));
                marks.setMaxMarks(resultSet.getDouble("max_marks"));

                marksList.add(marks);
            }

        } catch (Exception e) {
            System.out.println("Failed to load marks list!");
            e.printStackTrace();
        }

        return marksList;
    }

    // Get total number of marks records
    public int getTotalMarksRecords() {

        String sql = "SELECT COUNT(*) FROM marks";

        return (int) getAggregateValue(sql);
    }

    // Get average percentage across valid marks records
    public double getAveragePercentage() {

        String sql =
                "SELECT COALESCE(AVG(marks_obtained * 100.0 / max_marks), 0) "
                + "FROM marks WHERE max_marks > 0";

        return getAggregateValue(sql);
    }

    // Get highest percentage across valid marks records
    public double getHighestPercentage() {

        String sql =
                "SELECT COALESCE(MAX(marks_obtained * 100.0 / max_marks), 0) "
                + "FROM marks WHERE max_marks > 0";

        return getAggregateValue(sql);
    }

    // Get pass rate (50% or above) across valid marks records
    public double getPassRate() {

        String sql =
                "SELECT COALESCE("
                + "100.0 * SUM(CASE "
                + "WHEN marks_obtained * 100.0 / max_marks >= 50 "
                + "THEN 1 ELSE 0 END) / NULLIF(COUNT(*), 0), "
                + "0) "
                + "FROM marks WHERE max_marks > 0";

        return getAggregateValue(sql);
    }

    // Helper method to execute aggregate SQL queries
    private double getAggregateValue(String sql) {

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {

            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }

        } catch (Exception e) {

            System.out.println("Failed to calculate marks statistics!");
            e.printStackTrace();
        }

        return 0.0;
    }

    // Count marks records for one student
    public int getStudentMarksCount(int studentId) {

        String sql =
                "SELECT COUNT(*) FROM marks WHERE student_id = ?";

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

    // Calculate average marks percentage for one student
    public double getStudentAveragePercentage(int studentId) {

        String sql =
                "SELECT COALESCE("
                + "AVG(marks_obtained * 100.0 / NULLIF(max_marks, 0)), 0) "
                + "FROM marks WHERE student_id = ? AND max_marks > 0";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

}