
package studenthub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import studenthub.model.Subject;
import studenthub.util.DBConnection;

public class SubjectDAO {

    // =====================================
    // 1. ADD SUBJECT
    // =====================================
    public boolean addSubject(Subject subject) {

        String sql = "INSERT INTO subjects "
                   + "(subject_name, subject_code, semester) "
                   + "VALUES (?, ?, ?)";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, subject.getName());
            statement.setString(2, subject.getCourse());
            statement.setInt(3, subject.getSemester());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Subject added successfully!");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Failed to add subject!");
            e.printStackTrace();
        }

        return false;
    }


    // =====================================
    // 2. FETCH ALL SUBJECTS
    // =====================================
    public List<Subject> fetchAllSubjects() {

        List<Subject> subjects = new ArrayList<>();

        String sql = "SELECT subject_id, subject_name, "
                   + "subject_code, semester FROM subjects";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Subject subject = new Subject();

                subject.setId(
                    resultSet.getInt("subject_id")
                );

                subject.setName(
                    resultSet.getString("subject_name")
                );

                subject.setCourse(
                    resultSet.getString("subject_code")
                );

                subject.setSemester(
                    resultSet.getInt("semester")
                );

                subjects.add(subject);
            }

        } catch (SQLException e) {
            System.out.println("Failed to fetch subjects!");
            e.printStackTrace();
        }

        return subjects;
    }


    // =====================================
    // 3. DISPLAY ALL SUBJECTS IN CONSOLE
    // =====================================
    public void getAllSubjects() {

        List<Subject> subjects = fetchAllSubjects();

        System.out.println("----- Subject List -----");

        if (subjects.isEmpty()) {
            System.out.println("No subjects found.");
            return;
        }

        for (Subject subject : subjects) {

            System.out.println(
                subject.getId() + " | "
                + subject.getName() + " | "
                + subject.getCourse() + " | "
                + subject.getSemester()
            );
        }
    }


    // =====================================
    // 4. FIND SUBJECT BY ID
    // =====================================
    public Subject getSubjectById(int id) {

        String sql = "SELECT subject_id, subject_name, "
                   + "subject_code, semester "
                   + "FROM subjects WHERE subject_id = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Subject subject = new Subject();

                    subject.setId(
                        resultSet.getInt("subject_id")
                    );

                    subject.setName(
                        resultSet.getString("subject_name")
                    );

                    subject.setCourse(
                        resultSet.getString("subject_code")
                    );

                    subject.setSemester(
                        resultSet.getInt("semester")
                    );

                    return subject;
                }
            }

        } catch (SQLException e) {
            System.out.println("Failed to find subject!");
            e.printStackTrace();
        }

        return null;
    }


    // =====================================
    // 5. UPDATE SUBJECT
    // =====================================
    public boolean updateSubject(Subject subject) {

        String sql = "UPDATE subjects "
                   + "SET subject_name = ?, "
                   + "subject_code = ?, "
                   + "semester = ? "
                   + "WHERE subject_id = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, subject.getName());
            statement.setString(2, subject.getCourse());
            statement.setInt(3, subject.getSemester());
            statement.setInt(4, subject.getId());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Subject updated successfully!");
                return true;
            } else {
                System.out.println("Subject not found!");
            }

        } catch (SQLException e) {
            System.out.println("Failed to update subject!");
            e.printStackTrace();
        }

        return false;
    }


    // =====================================
    // 6. DELETE SUBJECT
    // =====================================
    public boolean deleteSubject(int id) {

        String sql = "DELETE FROM subjects WHERE subject_id = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Subject deleted successfully!");
                return true;
            } else {
                System.out.println("Subject not found!");
            }

        } catch (SQLException e) {
            System.out.println("Failed to delete subject!");
            e.printStackTrace();
        }

        return false;
    }
}
