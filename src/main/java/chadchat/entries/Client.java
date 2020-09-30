package chadchat.entries;

import chadchat.UI.Menu;
import chadchat.api.ChadChat;
import chadchat.domain.Message.Message;
import chadchat.domain.User.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

public class Client implements Runnable, ChadChat.MessageObserver {
    protected final Scanner in;
    protected final PrintWriter out;
    private final ChadChat chadChat;
    private final Socket socket;
    
    private int lastSeenMsg;
    
    private Menu menu;
    
    
    public Client(Scanner in, PrintWriter out, ChadChat chadChat, Socket socket) {
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
        for (Message m : chadChat.getNewMessages(lastSeenMsg)) {
            out.println(m);
            out.flush();
            lastSeenMsg = m.getId();
        }
    }
    
    @Override
    public void notifyUserBlocked() {
        for (User u: chadChat.getBlockedUsers()){
            if(u.equals(menu.getCurUser())){
                try {
                    socket.close();
                } catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    
    @Override
    public synchronized void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
    
            while (true) {
                menu = new Menu(in,out,chadChat,socket);
        
                menu.showMenu();
                out.flush();
        
            }
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
