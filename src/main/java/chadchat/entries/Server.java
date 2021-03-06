package chadchat.entries;

import chadchat.api.ChadChat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

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
    
    public static void stopServer(){
        keepRunning = false;
        System.exit(0);
    }
    
    public static void main(String[] args) throws IOException {
        final int port = 6999;
        final ServerSocket serverSocket = new ServerSocket(port);
        final ChadChat chadChat = new ChadChat();
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        
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
            Scanner in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true,StandardCharsets.UTF_8);
            
            Client client = new Client(in,out,chadChat,socket,this);
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
