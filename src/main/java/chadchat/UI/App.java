package chadchat.UI;

import chadchat.domain.Channel.Channel;
import chadchat.domain.Message.Message;
import chadchat.domain.User.User;
import chadchat.infrastructure.Database;

import java.io.IOException;
import java.util.Scanner;

public class App {

    // private final ServerSocket serverSocket = new ServerSocket(port);


    public static void main(String[] args) throws IOException {

        System.out.println("Server Started!");

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter you name please");
        String username = sc.nextLine();
        System.out.println("Create a new chat-channel, give it a name!");
        String channelname = sc.nextLine();
        System.out.println("Start chatting");
        String messageText = sc.nextLine();

        Database database = new Database();
        User user = database.createUser(username);
        Channel channel = database.createChannel(channelname);
        Message message = database.createMessage(messageText);
        System.out.println("You're now connected \n" + user +  "\nChannel " + channel + "\nMess: " + message);
    }
}

