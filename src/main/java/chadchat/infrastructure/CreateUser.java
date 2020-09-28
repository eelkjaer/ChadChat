package chadchat.infrastructure;

import chadchat.domain.User;

import java.sql.*;
import java.util.Calendar;
import java.util.NoSuchElementException;

import static chadchat.infrastructure.Database.getConnection;
import static java.sql.Types.TIMESTAMP;

public class CreateUser {

    private User createUser(String userName) {
        int id = -1;
        try (Connection conn = Database.getConnection()) {
            String sql;
            sql = "INSERT INTO User(userName) VALUES (?)";

            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, userName);


            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt(1);

            } else {
                System.out.println("elsa");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return findUser(id);
    }

    public User findUser(int id) throws NoSuchElementException {
        try(Connection conn = getConnection()) {
            PreparedStatement s = conn.prepareStatement(
                    "SELECT * FROM user WHERE id = ?;");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            if(rs.next()) {
                return loadUser(rs);
            } else {
                System.err.println("No version in properties.");
                throw new NoSuchElementException("No user with id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private User loadUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("user.id"),
                rs.getString("user.userName"),
                rs.getTimestamp("user.timestamp").toLocalDateTime());
        // rs.getBytes("users.salt"),
        // rs.getBytes("users.secret"));
    }

    public static void main(String[] args) {

        System.out.println(new CreateUser().createUser("Janus"));
    }

}
