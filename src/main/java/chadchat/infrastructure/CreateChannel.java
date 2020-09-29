package chadchat.infrastructure;

import java.sql.*;




public class CreateChannel {
    /*

    private static void createChannel(String name)  {
        try (Connection conn = Database.getConnection()) {
            String sql;
            sql = "INSERT INTO Channels(name) VALUES (?)";

            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            rs.next();

            int id = rs.getInt(1);
            System.out.println(id);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Remove this soon
        createChannel("Peter");
    }

     */
}
