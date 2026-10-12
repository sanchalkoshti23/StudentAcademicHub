
package studenthub.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/student_academic_hub?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {

        if (USER == null || PASSWORD == null) {
            throw new SQLException(
                "Database credentials are missing. Set DB_USER and DB_PASSWORD."
            );
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
