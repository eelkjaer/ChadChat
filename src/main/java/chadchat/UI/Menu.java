package chadchat.UI;

import chadchat.api.ChadChat;
import chadchat.domain.Channel.Channel;
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
    private final ChadChat chadChat;
    private final Scanner in;
    private final PrintWriter out;
    private final Socket socket;
    private final Client client;
    
    private User curUser;
    private Channel curChannel;
    
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
            if(tmpMsg.getChannel().equals(curChannel)){
                out.println(tmpMsg);
                out.flush();
            }
        }
    }

    public void createChannel() {
        out.print("Write Channel name: ");
        out.flush();
        String channelName = in.nextLine();
        Channel tmpChannel = chadChat.createChannel(channelName, curUser);
        
        if(tmpChannel != null){
            curChannel = tmpChannel;
            chadChat.setCurrentChannel(curUser,curChannel);
        } else {
            out.println("Error creating channel. Try again.");
            createChannel();
        }
    }

    public void loadChannels() {
        out.println("\nAvailable Chatrooms");
        for (Channel channel : chadChat.getAllChannels()) {
            if(channel.equals(curChannel)){
                out.println(channel + " <Currently in this>");
            } else {
                out.println(channel);
            }
            out.flush();
        }
    }
    
    private HashMap<String, String> helpMenu(){
        HashMap<String, String> menuItems = new HashMap<>();
        menuItems.put("help","Shows all available commands");
        menuItems.put("channels","Show all available channels");
        menuItems.put("changechannel","Changes to desired channel");
        menuItems.put("createchannel","Create a new channel");
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
        out.flush();
        boolean chatting = true;
        while (chatting) {
            try {
                out.print("> ");
                out.flush();
                String msg = in.nextLine();
                System.out.println("MSG: " + msg);
                
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
                    case "!channels":
                        loadChannels();
                        break;
                    case "!createchannel":
                        createChannel();
                        break;
                    case "!changechannel":
                        changeChannel();
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
                        Message tmpMsg = new Message(msg, curChannel);
                        chadChat.createMessage(curUser, tmpMsg);
                        break;
                }

            } catch (Exception e) {
                out.println(e.getMessage());
                out.flush();
                    if (curUser != null ) {
                        chatting = false;
                        logout();
                    }
                }
            }
        }
        

        private void changeChannel(){
            out.print("Enter channel id: ");
            out.flush();
            int id = Integer.parseInt(in.nextLine());
            
            Channel tmpChannel = chadChat.getChannelById(id);
            if(tmpChannel != null){
                chadChat.setCurrentChannel(curUser,tmpChannel);
                curChannel = chadChat.getCurrentUserChannel(curUser);
                out.println("You are now in: " + curChannel.getChannelName());
            } else {
                out.println("Not valid channel id! Try again.");
                out.flush();
                changeChannel();
            }
        }
        
    private void createUser() {
        String userName = "";
        String password = "";
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
            curChannel = chadChat.getChannelById(1);
            chadChat.setCurrentChannel(curUser,curChannel);
            showChat(); //Loads the chat.
        } finally {
            logout();
        }
    }


}

