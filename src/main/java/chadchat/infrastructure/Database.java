package chadchat.infrastructure;

import chadchat.domain.Channel.Channel;
import chadchat.domain.Message.Message;
import chadchat.domain.Message.MessageExists;
import chadchat.domain.Message.MessageRepository;
import chadchat.domain.User.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.TimeZone;

public class Database implements MessageRepository {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/ChadChat?serverTimezone=" + TimeZone.getDefault().getID();

    // Database credentials
    static final String USER = "chadchat";
    static final String PASS = "familiebil";

    // Database version
    private static final int version = 3;

    public Database() {
        if (getCurrentVersion() != getVersion()) {
            throw new IllegalStateException("Database in wrong state, expected:"
                    + getVersion() + ", got: " + getCurrentVersion());
        }
    }

    public static int getCurrentVersion() {
        try (Connection conn = getConnection()) {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT value FROM properties WHERE name = 'version';");
            if(rs.next()) {
                String column = rs.getString("value");
                return Integer.parseInt(column);
            } else {
                System.err.println("No version in properties.");
                return -1;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);

    }

    public static int getVersion() {
        return version;
    }
    
    public User checkLogin(String username){
        try (Connection conn = Database.getConnection()) {
            var stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM User WHERE userName='"+username+"'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("userName"),
                        rs.getTimestamp("timestamp").toLocalDateTime(),
                        rs.getBoolean("admin"),
                        rs.getBytes("salt"),
                        rs.getBytes("secret"));
            }
            System.out.println("You're connected to CHADCHAT");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User createUser(String userName, byte[] salt, byte[] secret) {
        int id = -1;
        try (Connection conn = Database.getConnection()) {
            String sql;
            sql = "INSERT INTO User(userName, salt, secret) VALUES (?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, userName);
            stmt.setBytes(2, salt);
            stmt.setBytes(3, secret);
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
                rs.getTimestamp("user.timestamp").toLocalDateTime(),
                rs.getBoolean("user.admin"),
                rs.getBytes("user.salt"),
                rs.getBytes("user.secret"));
    }


    public Channel createChannel(String channelName, User owner) {
        int id = -1;
        try (Connection conn = Database.getConnection()) {
            String sql;
            sql = "INSERT INTO Channels(name, ownerId) VALUES (?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, channelName);
            stmt.setInt(2,owner.getId());
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
        return findChannel(id);
    }

    public Channel findChannel(int id) throws NoSuchElementException {
        try(Connection conn = getConnection()) {
            PreparedStatement s = conn.prepareStatement(
                    "SELECT * FROM channels WHERE id = ?;");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            if(rs.next()) {
                return loadChannel(rs);
            } else {
                System.err.println("No version in properties.");
                throw new NoSuchElementException("No user with id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Channel loadChannel(ResultSet rs) throws SQLException {
        return new Channel(
                rs.getInt("channels.id"),
                rs.getString("channels.name"),
                findUser(rs.getInt("channels.ownerid"))
                );
    }

    // Load in Channels to channelList
    //@Override
    public Iterable<Channel> findAllChannels() {
        try (Connection conn = getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Channels;");
            ResultSet rs = s.executeQuery();
            ArrayList<Channel> channelList = new ArrayList<>();
            while(rs.next()) {
                channelList.add(loadChannel(rs));
            }
            return channelList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Message findMessage(int id) throws NoSuchElementException {
        try(Connection conn = getConnection()) {
            PreparedStatement s = conn.prepareStatement(
                    "SELECT * FROM messages WHERE id = ?;");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            if(rs.next()) {
                return loadMessage(rs);
            } else {
                System.err.println("No version in properties.");
                throw new NoSuchElementException("No user with id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Message loadMessage(ResultSet rs) throws SQLException {
        return new Message(
                rs.getInt("messages.id"),
                rs.getString("messages.messageText"),
                rs.getTimestamp("messages.timestamp").toLocalDateTime(),
                findUser(rs.getInt("messages.userid")),
                findChannel(rs.getInt(("messages.channelId"))));
    }
    
    @Override
    public Iterable<Message> findAllMessages(int lastSeenMsg) {
        try (Connection conn = getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Messages WHERE id>?;");
            s.setInt(1,lastSeenMsg);
            ResultSet rs = s.executeQuery();
            ArrayList<Message> items = new ArrayList<>();
            while(rs.next()) {
                items.add(loadMessage(rs));
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Message createMessage(String messageText, User user, Channel channel) {
        int id;
        try (Connection conn = getConnection()) {
            var ps =
                    conn.prepareStatement(
                            "INSERT INTO Messages (messageText, userid, channelId) " +
                                    "VALUE (?,?,?);",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, messageText);
            ps.setInt(2,user.getId());
            ps.setInt(3,channel.getId());
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new MessageExists();
            }
        
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new MessageExists(rs.getInt(1));
            }
        } catch (SQLException | MessageExists e) {
            throw new RuntimeException(e);
        }
        return findMessage(id);
    }


}
