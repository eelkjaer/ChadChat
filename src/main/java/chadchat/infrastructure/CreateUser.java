package chadchat.infrastructure;

import chadchat.domain.User;

import java.sql.*;
import java.util.Calendar;

import static java.sql.Types.TIMESTAMP;

public class CreateUser {

        // The entry point of the ChatChad server

        // JDBC driver name and database URL
        static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/chadchat";

    // Database credentials
    static final String USER = "chadchat";
    // static final String PASS = "null";

        private static User createUser(String userName, Timestamp timestamp) throws ClassNotFoundException {
            Class.forName(JDBC_DRIVER);
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, null)) {
                String sql;
                sql = "INSERT INTO User(userName) VALUES (?)";

                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, userName);

                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    return new User(rs.getInt(1), userName, timestamp);

                } else {
                    System.out.println("elsa");
                    return null;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }

        public static void main(String[] args) throws SQLException, ClassNotFoundException {
            // Remove this soon
            createUser("Emil", Timestamp.valueOf(("2020-05-01 12:30:00")));

        }

    }
