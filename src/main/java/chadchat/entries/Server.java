package chadchat.entries;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable {

    private final Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            System.out.println("Started");
            out.println("Started");

            String line = null;
            while (!(line = in.nextLine()).strip().equals("quit")) {
                out.println("You said: " + line);
                out.flush();
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        final int port = 6860;
        final ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("[CONNECTED] " + socket.getInetAddress() + " port " + socket.getLocalPort());

            Thread thread = new Thread(new Server(socket));
            thread.start();
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
