package chadchat.entries;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class RunServer implements Runnable {
    
    private final Socket socket;
    public static volatile boolean keepRunning = true;
    
    public RunServer(Socket socket) {
        this.socket = socket;
    }
    
    private String timestamp(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
    
    @Override
    public synchronized void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            
            Server s = new Server(in, out);
            s.setSocket(socket);
            s.run();
            
            System.out.println(timestamp() + " [DISCONNECTED] " + socket.getInetAddress()
                    + " port " + socket.getPort()
                    + " server port " + socket.getLocalPort());
            
            socket.close();
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException {
        final int port = 6969;
        final ServerSocket serverSocket = new ServerSocket(port);
        String timestamp = new SimpleDateFormat("HH:MM:ss").format(new Date());
        
        while(keepRunning) {
            Socket socket = serverSocket.accept();
            System.out.println(timestamp + " [CONNECTED] " + socket.getInetAddress()
                    + " port " + socket.getPort()
                    + " server port " + socket.getLocalPort());
            
            Thread thread = new Thread(new RunServer(socket));
            thread.start();
        }
    }
}
