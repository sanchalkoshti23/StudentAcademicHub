package studenthub;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import studenthub.util.DBConnection;

public class Main {

    public static void main(String[] args) {

        try {
            Connection connection = DBConnection.getConnection();

            System.out.println("Database connected successfully!");

            Statement statement = connection.createStatement();

            String query = "SELECT * FROM students";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                int studentId = resultSet.getInt("student_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String college = resultSet.getString("college");
                String branch = resultSet.getString("branch");
                int year = resultSet.getInt("year");

                System.out.println(
                    studentId + " | " +
                    name + " | " +
                    email + " | " +
                    college + " | " +
                    branch + " | " +
                    year
                );
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Database operation failed!");
            e.printStackTrace();
        }
    }
}