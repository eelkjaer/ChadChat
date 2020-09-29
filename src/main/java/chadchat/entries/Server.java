package chadchat.entries;

import chadchat.UI.Menu;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    
    protected final Scanner in;
    protected final PrintWriter out;
    protected boolean running = true;
    private Socket socket = null;
    
    public Server(Scanner in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }
    
    public void setSocket(Socket socket){
        this.socket = socket;
    }
    
    public void quit(){
        try {
            socket.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void run() {
            while (running) {
                Menu menu = new Menu(in, out);
                
                menu.showMenu();
                out.flush();
            
        }
    }
}


    /*

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
    /*

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

     */
