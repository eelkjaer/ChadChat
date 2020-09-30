package chadchat.entries.EmilTesting;

import chadchat.api.ChadChat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class StartServer implements Runnable{
    
    public static volatile boolean keepRunning = true;
    private final Socket socket;
    private final ChadChat chadChat;
    
    public StartServer(Socket socket, ChadChat chadChat) {
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
            
            Thread thread = new Thread(new StartServer(socket, chadChat));
            thread.start();
        }
    }
    
    @Override
    public synchronized void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            
            Klient klient = new Klient(in,out,chadChat,socket);
            chadChat.registerMessageObserver(klient);
            klient.run();
            
            System.out.println(timestamp() + " [DISCONNECTED] " + socket.getInetAddress()
                    + " port " + socket.getPort()
                    + " server port " + socket.getLocalPort());
            
            socket.close();
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
