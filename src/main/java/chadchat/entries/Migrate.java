package chadchat.entries;

import chadchat.api.ChadChat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


import chadchat.infrastructure.Database;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */
public class Migrate {

    public static void main(String[] args) throws IOException, SQLException {
        runMigrations();

    }

    public static void runMigrations() throws IOException, SQLException {
        int version = Database.getCurrentVersion();
        while (version < Database.getVersion()) {
            System.out.printf("Current DB version %d is smaller than expected %d\n", version, Database.getVersion());
            runMigration(version + 1);
            int new_version = Database.getCurrentVersion();
            if (new_version > version) {
                version = new_version;
                System.out.println("Updated database to version: " + new_version);
            } else {
                throw new RuntimeException("Something went wrong, version not increased: " + new_version);
            }

        }
    }

    public static void runMigration(int i) throws IOException, SQLException {
        String migrationFile = String.format("migrate/%d.sql", i);
        System.out.println("Running migration: " + migrationFile);
        InputStream stream = Migrate.class.getClassLoader().getResourceAsStream(migrationFile);
        if (stream == null) {
            System.out.println("Migration file, does not exist: " + migrationFile);
            throw new FileNotFoundException(migrationFile);
        }
        try(Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setStopOnError(true);
            runner.runScript(new BufferedReader(new InputStreamReader(stream)));
            conn.commit();
        }
        System.out.println("Done running migration");
    }

}




/*
public class Server implements Runnable{

    public static volatile boolean keepRunning = true;
    private final Socket socket;
    private final ChadChat chadChat;

    public Server(Socket socket, ChadChat chadChat) {
        this.socket = socket;
        this.chadChat = chadChat;
    }

    private String timestamp(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static void main(String[] args) throws IOException {
        final int port = 6999;
        final ServerSocket serverSocket = new ServerSocket(port);
        final ChadChat chadChat = new ChadChat();
        String timestamp = new SimpleDateFormat("HH:MM:ss").format(new Date());

        while(keepRunning) {
            Socket socket = serverSocket.accept();
            System.out.println(timestamp + " [CONNECTED] " + socket.getInetAddress()
                    + " port " + socket.getPort()
                    + " server port " + socket.getLocalPort());

            Thread thread = new Thread(new Server(socket, chadChat));
            thread.start();
        }
    }

    @Override
    public synchronized void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            Client client = new Client(in,out,chadChat,socket);
            chadChat.registerMessageObserver(client);
            client.run();

            System.out.println(timestamp() + " [DISCONNECTED] " + socket.getInetAddress()
                    + " port " + socket.getPort()
                    + " server port " + socket.getLocalPort());

            socket.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

 */
