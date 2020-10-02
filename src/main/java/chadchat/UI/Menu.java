package chadchat.UI;

import chadchat.api.ChadChat;
import chadchat.domain.Message.Message;
import chadchat.domain.User.InvalidPassword;
import chadchat.domain.User.User;
import chadchat.entries.Client;
import chadchat.infrastructure.Database;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;


public class Menu {

    private final Beautifier beauty = new Beautifier();
    private final Database db = new Database();
    private User curUser;
    private final ChadChat chadChat;
    private final Scanner in;
    private final PrintWriter out;
    private final Socket socket;
    private final Client client;
    
    private final List<String> illegalUsernames = new ArrayList<>() {
        {
            add("system");
            add("sys");
            add("admin");
            add("administrator");
        }
    };

    public Menu(Scanner in, PrintWriter out, ChadChat chadChat, Socket socket, Client client) {
        this.chadChat = chadChat;
        this.in = in;
        this.out = out;
        this.socket = socket;
        this.client = client;
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
                    logout();
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
    
    private void logout() {
        chadChat.logout(curUser, socket);
        curUser = null;
        client.logout();
    }
    
    public void loadChat() {
        for (Message tmpMsg : db.findAllMessages(1)) {
            out.println(tmpMsg);
            out.flush();
        }
    }
    
    private HashMap<String, String> helpMenu(){
        HashMap<String, String> menuItems = new HashMap<>();
        menuItems.put("Channel","Create a new chat channel");
        menuItems.put("help","Shows all available commands");
        menuItems.put("quit","Will log you out.");
        menuItems.put("users","Lists all active users");
        
        if(curUser.isAdmin()){
            menuItems.put("kick","Kicks desired user from the chat");
            menuItems.put("shutdown","Makes the server shutdown completly");
        }
        
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
                        logout();
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
                        out.print("Enter username to kick: ");
                        out.flush();
                        String username = in.nextLine();
                        User userToKick = chadChat.findActiveUser(username);
                        chadChat.setBlocked(userToKick, curUser);
                        chadChat.removeBlocked(userToKick);
                        break;
                    case "!shutdown":
                        if(!curUser.isAdmin()) {
                            out.println("Action not allowed!");
                            break;
                        }
                        out.print("Are you sure? [Y]es or [N]o: ");
                        out.flush();
                        String action = in.nextLine();
                        if(action.contains("y")){
                            out.println("Server will now shutdown! Goodbye.");
                            out.flush();
                            client.stopServer();
                            logout();
                        } else {
                            out.println("Server will not shutdown!");
                            out.flush();
                            break;
                        }
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
        String userName = "";
        String password = "";
        int maxAttemps = 5;
        out.print("Enter username: ");
        out.flush();
        userName = in.nextLine();
    
        if(illegalUsernames.contains(userName)){ //Checks that username is not illegal.
            out.print("Illegal username. Try again!\nUsername: ");
            out.flush();
            createUser();
        }
    
        for(User userLogggedIn: chadChat.getActiveUsers()){ //Checks that user is not already logged in.
            if(userLogggedIn.getUserName().equalsIgnoreCase(userName)){
                out.print("Already logged in. Try again!\nUsername: ");
                out.flush();
                createUser();
            }
        }
    
        out.print("Enter password: ");
        out.flush();
        password = in.nextLine();
    
        curUser = chadChat.createUser(userName, password);

        out.println("Welcome to ChadChat, " + userName);
        out.flush();
    
        try {
            chadChat.notifyNewUser(curUser);
            showChat(); //Loads the chat.
        } finally {
            logout();
        }

    }

    private void loginCheck() {
        String userName = "";
        String password = "";
        int maxAttemps = 5;
        out.print("Enter your username: ");
        out.flush();
        userName = in.nextLine();
        
        if(illegalUsernames.contains(userName)){ //Checks that username is not illegal.
            out.print("Illegal username. Try again!\nUsername: ");
            out.flush();
            loginCheck();
        }
        
        for(User userLogggedIn: chadChat.getActiveUsers()){ //Checks that user is not already logged in.
            if(userLogggedIn.getUserName().equalsIgnoreCase(userName)){
                out.print("Already logged in. Try again!\nUsername: ");
                out.flush();
                loginCheck();
            }
        }
    
        out.print("Enter your password: ");
        out.flush();
        password = in.nextLine();
        

        try {
            curUser = chadChat.userLogin(userName, password);
        } catch (InvalidPassword invalidPassword) {
            invalidPassword.printStackTrace();
        }

        if(curUser == null){ //Checks that user actually exists.
            while(curUser == null) {
                maxAttemps--;
                if(maxAttemps == 0){
                    try {
                        logout();
                    } catch (Exception e){
                        System.out.println(e);
                    }
                }
                out.print("Wrong username/password. " + maxAttemps + " attemps left. Try again!\nUsername: ");
                out.flush();
                userName = in.nextLine();
                out.print("\nPassword: ");
                out.flush();
                password = in.nextLine();
                try {
                    curUser = chadChat.userLogin(userName, password);
                } catch (InvalidPassword invalidPassword) {
                    invalidPassword.printStackTrace();
                }
            }
        }
    
        out.println("Welcome to ChadChat, " + userName);
        System.out.println("Connected: " + curUser);
        try {
            showChat(); //Loads the chat.
        } finally {
            logout();
        }
    }
}

