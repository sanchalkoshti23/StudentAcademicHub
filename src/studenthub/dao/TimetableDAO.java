package studenthub.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import studenthub.model.Timetable;
import studenthub.util.DBConnection;

public class TimetableDAO {

    // Add timetable
    public void addTimetable(Timetable timetable) {

        String sql = "INSERT INTO timetable "
                   + "(subject_id, day_of_week, start_time, end_time, room) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, timetable.getSubjectId());
            statement.setString(2, timetable.getDayOfWeek());
            statement.setTime(3,
                    java.sql.Time.valueOf(timetable.getStartTime()));
            statement.setTime(4,
                    java.sql.Time.valueOf(timetable.getEndTime()));
            statement.setString(5, timetable.getRoom());

            statement.executeUpdate();

            System.out.println(
                    "Timetable added successfully!");

            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println(
                    "Failed to add timetable!");

            e.printStackTrace();
        }
    }
 // Get all timetable entries
    public void getAllTimetable() {

        String sql = "SELECT * FROM timetable";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            java.sql.ResultSet resultSet =
                    statement.executeQuery();

            System.out.println("----- Timetable List -----");

            while (resultSet.next()) {

                int timetableId =
                        resultSet.getInt("timetable_id");

                int subjectId =
                        resultSet.getInt("subject_id");

                String dayOfWeek =
                        resultSet.getString("day_of_week");

                java.sql.Time startTime =
                        resultSet.getTime("start_time");

                java.sql.Time endTime =
                        resultSet.getTime("end_time");

                String room =
                        resultSet.getString("room");

                System.out.println(
                        timetableId + " | "
                        + subjectId + " | "
                        + dayOfWeek + " | "
                        + startTime + " | "
                        + endTime + " | "
                        + room
                );
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println(
                    "Failed to get timetable!");

            e.printStackTrace();
        }
    }
 // Get timetable by ID
    public Timetable getTimetableById(int id) {

        String sql =
                "SELECT * FROM timetable WHERE timetable_id = ?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, id);

            java.sql.ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                Timetable timetable = new Timetable();

                timetable.setTimetableId(
                        resultSet.getInt("timetable_id"));

                timetable.setSubjectId(
                        resultSet.getInt("subject_id"));

                timetable.setDayOfWeek(
                        resultSet.getString("day_of_week"));

                timetable.setStartTime(
                        resultSet.getTime("start_time").toLocalTime());

                timetable.setEndTime(
                        resultSet.getTime("end_time").toLocalTime());

                timetable.setRoom(
                        resultSet.getString("room"));

                resultSet.close();
                statement.close();
                connection.close();

                return timetable;
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println(
                    "Failed to find timetable!");

            e.printStackTrace();
        }

        return null;
    }
 // Update timetable
    public void updateTimetable(Timetable timetable) {

        String sql = "UPDATE timetable "
                   + "SET subject_id = ?, "
                   + "day_of_week = ?, "
                   + "start_time = ?, "
                   + "end_time = ?, "
                   + "room = ? "
                   + "WHERE timetable_id = ?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, timetable.getSubjectId());

            statement.setString(2, timetable.getDayOfWeek());

            statement.setTime(3,
                    java.sql.Time.valueOf(
                            timetable.getStartTime()));

            statement.setTime(4,
                    java.sql.Time.valueOf(
                            timetable.getEndTime()));

            statement.setString(5, timetable.getRoom());

            statement.setInt(6, timetable.getTimetableId());

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated > 0) {

                System.out.println(
                        "Timetable updated successfully!");

            } else {

                System.out.println(
                        "Timetable not found!");
            }

            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println(
                    "Failed to update timetable!");

            e.printStackTrace();
        }
    }
 // Delete timetable
    public void deleteTimetable(int id) {

        String sql =
                "DELETE FROM timetable WHERE timetable_id = ?";

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
                        "Timetable deleted successfully!");

            } else {

                System.out.println(
                        "Timetable not found!");
            }

            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println(
                    "Failed to delete timetable!");

            e.printStackTrace();
        }
    }
 // Get timetable by day
    public void getTimetableByDay(String day) {

        String sql =
                "SELECT * FROM timetable WHERE day_of_week = ?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1, day);

            java.sql.ResultSet resultSet =
                    statement.executeQuery();

            System.out.println(
                    "----- Timetable for " + day + " -----");

            while (resultSet.next()) {

                int timetableId =
                        resultSet.getInt("timetable_id");

                int subjectId =
                        resultSet.getInt("subject_id");

                String dayOfWeek =
                        resultSet.getString("day_of_week");

                java.sql.Time startTime =
                        resultSet.getTime("start_time");

                java.sql.Time endTime =
                        resultSet.getTime("end_time");

                String room =
                        resultSet.getString("room");

                System.out.println(
                        timetableId + " | "
                        + subjectId + " | "
                        + dayOfWeek + " | "
                        + startTime + " | "
                        + endTime + " | "
                        + room
                );
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println(
                    "Failed to get timetable by day!");

            e.printStackTrace();
        }
    }
    public List<Timetable> getAllTimetableList() {
        List<Timetable> timetables = new ArrayList<>();

        String sql = "SELECT * FROM timetable ORDER BY day_of_week, start_time";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Timetable timetable = new Timetable();

                timetable.setTimetableId(resultSet.getInt("timetable_id"));
                timetable.setSubjectId(resultSet.getInt("subject_id"));
                timetable.setDayOfWeek(resultSet.getString("day_of_week"));
                timetable.setStartTime(
                    resultSet.getTime("start_time").toLocalTime()
                );
                timetable.setEndTime(
                    resultSet.getTime("end_time").toLocalTime()
                );
                timetable.setRoom(resultSet.getString("room"));

                timetables.add(timetable);
            }
        } catch (Exception e) {
            System.out.println("Failed to load timetable entries!");
            e.printStackTrace();
        }

        return timetables;
    }
}