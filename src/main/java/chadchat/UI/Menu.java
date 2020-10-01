package chadchat.UI;

import chadchat.api.ChadChat;
import chadchat.domain.Message.Message;
import chadchat.domain.User.InvalidPassword;
import chadchat.domain.User.User;
import chadchat.entries.Client;
import chadchat.infrastructure.Database;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Menu {

    private final Beautifier beauty = new Beautifier();
    private final Database db = new Database();
    private User curUser;
    private final ChadChat chadChat;
    private final Scanner in;
    private final PrintWriter out;
    private final Socket socket;
    
    private final List<String> illegalUsernames = new ArrayList<>() {
        {
            add("system");
            add("sys");
            add("admin");
            add("administrator");
        }
    };

    public Menu(Scanner in, PrintWriter out, ChadChat chadChat, Socket socket) {
        this.chadChat = chadChat;
        this.in = in;
        this.out = out;
        this.socket = socket;
    }
    
    public User getCurUser(){
        return curUser;
    }

    public void showMenu() {
        String[] menuItems = new String[]{"Login", "Register"}; //Array med menupunkter.
        out.print(beauty.generateMenuStr(menuItems)); //Printer menuen.
        out.flush();

        try {
            out.print("> ");
            out.flush();
            int input = Integer.parseInt(in.nextLine());
            switch (input) {
                case 0:
                    out.println("Goodbye! \uD83D\uDE00");
                    out.flush();
                    chadChat.logout(curUser);
                case 1:
                    out.println(menuItems[input - 1] + " Selected");
                    out.flush();
                    loginCheck();
                    break;
                case 2:
                    out.println(menuItems[input - 1] + " Selected");
                    out.flush();
                    createUser();
                    break;
                default:
                    out.println(menuItems[input - 1] + " Selected");
                    out.flush();
                    showMenu();

            }
        } catch (Exception e) {
            out.println(e.getMessage());
            out.flush();

        }
    }

    public void loadChat() {
        for (Message tmpMsg : db.findAllMessages(1)) {
            out.println(tmpMsg.getTimestamp().toLocalTime().toString() +
                    " " + tmpMsg.getUser().getUserName() +
                    " said: " + tmpMsg.getMessageText());
            out.flush();
        }
    }
    
    private HashMap<String, String> helpMenu(){
        HashMap<String, String> menuItems = new HashMap<>();
        menuItems.put("help","Shows all available commands");
        menuItems.put("quit","Will log you out.");
        menuItems.put("users","Lists all active users");
        
        return menuItems;
    }

    public void showChat() {
        loadChat();
        boolean chatting = true;
        out.flush();
        while (chatting) {
            try {
                out.print("> ");
                out.flush();

                String msg = in.nextLine();
                
                switch (msg){
                    case "!help":
                        out.print(beauty.generateCmdMenuStr(helpMenu()));
                        break;
                    case "!quit":
                        out.flush();
                        chatting = false;
                        continue;
                    case "!users":
                        out.println("\nActive users:");
                        for(User u: chadChat.getActiveUsers()){
                            out.println(u.getUserName());
                            out.flush();
                        }
                        break;
                    case "!kick":
                        if(!curUser.isAdmin()) {
                            out.println("Action not allowed!");
                            break;
                        }
                        out.print("Enter username: ");
                        out.flush();
                        String username = in.nextLine();
                        User user = chadChat.findActiveUser(username);
                        chadChat.setBlocked(user, curUser);
                        chadChat.removeBlocked(user);
                        
                        break;
                    default:
                        chadChat.createMessage(curUser, msg);
                        break;
                }

            } catch (Exception e) {
                out.println(e.getMessage());
                out.flush();

            }
        }
    }

    private void createUser() {
        out.print("Enter your name: ");
        out.flush();
        String userName = in.nextLine();
        out.print("\nEnter a password: ");
        out.flush();
        String password = in.nextLine();
        chadChat.createUserAndLogin(userName, password);

        out.println("Welcome to ChadChat, " + userName);
        out.flush();

        showChat();

    }

    private void loginCheck() {
        String userName;
        out.print("Enter your username: ");
        out.flush();
        userName = in.nextLine();
        
        if(illegalUsernames.contains(userName)){
            out.print("Illegal username. Try again!\nUsername: ");
            out.flush();
            loginCheck();
        }
        
        for(User userLogggedIn: chadChat.getActiveUsers()){
            if(userLogggedIn.getUserName().equalsIgnoreCase(userName)){
                out.print("Already logged in. Try again!\nUsername: ");
                out.flush();
                loginCheck();
            }
        }

        try {
            curUser = chadChat.userLogin(userName, "1234");
        } catch (InvalidPassword invalidPassword) {
            invalidPassword.printStackTrace();
        }

        while (true) {
            if (!(curUser == null)) {
                out.println("Welcome to ChadChat, " + userName);
                System.out.println("Connected: " + curUser);
                break;
            } else {
                out.print("Wrong username. Try again!\nUsername: ");
                out.flush();
                userName = in.nextLine();
                curUser = db.checkLogin(userName);
            }
        }
        try {
            showChat();
        } finally {
            chadChat.logout(curUser);
            curUser = null;
        }
    }
}

