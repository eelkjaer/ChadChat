package chadchat.entries;

import chadchat.domain.Channel;
import chadchat.domain.Message;
import chadchat.domain.User;

import java.sql.*;

public class Server {
    // The entry point of the ChatChad server

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://104.248.135.65/ChadChat";

    //  Database credentials
    static final String USER = "chadchat";
    static final String PASS = "familiebil";

    /**
     * This is purely a data base test. Given that you have created a
     * users table in chatchad with an id and name.
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */

    private static void dbTest() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            var stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM User";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getTimestamp("createdAt").toLocalDateTime());
                System.out.println(user);
            }
            System.out.println("You're connected to CHADCHAT");
        }
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Remove this soon
        dbTest();

    }
}