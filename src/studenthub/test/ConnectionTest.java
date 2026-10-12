package studenthub.test;

import java.sql.Connection;
import studenthub.util.DBConnection;

public class ConnectionTest {

    public static void main(String[] args) {

        try {
            Connection connection = DBConnection.getConnection();

            System.out.println("Database connected successfully!");

            connection.close();

        } catch (Exception e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}