package chadchat.entries;

import chadchat.UI.Menu;
import chadchat.api.ChadChat;
import chadchat.domain.Channel.Channel;
import chadchat.domain.Message.Message;
import chadchat.domain.User.User;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Client implements Runnable, ChadChat.MessageObserver {
    protected final Scanner in;
    protected final PrintWriter out;
    private final ChadChat chadChat;
    private final Socket socket;
    private final Server server;
    
    private int lastSeenMsg;
    
    private volatile boolean shutdown;
    
    private Menu menu;
    
    
    public Client(Scanner in, PrintWriter out, ChadChat chadChat, Socket socket, Server server) {
        this.in = in;
        this.out = out;
        this.chadChat = chadChat;
        this.socket = socket;
        this.server = server;
    }
    
    public void stopServer(){
        Server.stopServer();
    }
    
    private String timestamp(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
    
    public void logout(){
        shutdown = true;
    }
    
    @Override
    public void notifyNewMessages(Channel channel) {
        for (Message m : chadChat.getNewMessages(lastSeenMsg)) {
            if(m.getChannel().equals(channel)){
                out.println(m);
                out.flush();
                lastSeenMsg = m.getId();
            }
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
        while (!shutdown) {
            try {
                Scanner in = new Scanner(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                
                menu = new Menu(in, out, chadChat, socket, this);
                menu.showMenu();
                out.flush();
            }
            catch (IOException e) {
                System.out.println("Er det her?");
                e.getMessage();
            }
        }
    }
}
