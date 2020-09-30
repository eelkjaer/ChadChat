package chadchat.entries.EmilTesting;

import chadchat.UI.Menu;
import chadchat.api.ChadChat;
import chadchat.domain.Message.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

public class Klient implements Runnable, ChadChat.MessageObserver {
    private final Scanner in;
    private final PrintWriter out;
    private final ChadChat chadChat;
    private final Socket socket;
    
    public Klient(Scanner in, PrintWriter out, ChadChat chadChat, Socket socket) {
        this.in = in;
        this.out = out;
        this.chadChat = chadChat;
        this.socket = socket;
    }
    
    private String timestamp(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
    
    @Override
    public void notifyNewMessages() {
        for (Message m : chadChat.getNewMessages()) {
            out.println(m);
            out.flush();
        }
    }
    
    @Override
    public synchronized void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
    
            while (true) {
                Menu menu = new Menu(in, out, chadChat);
        
                menu.showMenu();
                out.flush();
        
            }
            
            //socket.close();
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
