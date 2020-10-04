package chadchat.domain.Message;

import chadchat.domain.Channel.Channel;
import chadchat.domain.User.User;

import java.time.LocalDateTime;

public class Message {
    private final int id;
    private final String messageText;
    private final LocalDateTime timestamp;
    private final User user;
    private final Channel channel;

    public Message(int id, String messageText, LocalDateTime timestamp, User user, Channel channel) {
        this.id = id;
        this.messageText = messageText;
        this.timestamp = timestamp;
        this.user = user;
        this.channel = channel;
    }
    
    public Message(String messageText){
        this.user = null;
        this.id = -1;
        this.messageText = messageText;
        this.timestamp = null;
        this.channel = null;
    }

    public int getId() {
        return id;
    }

    public String getMessageText() {
        return messageText;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public User getUser() {
        return user;
    }
    
    public Channel getChannel(){
        return channel;
    }
    
    @Override
    public String toString() {
        String username = user.getUserName();
        
        //if(user.isAdmin()) username = "<ADMIN> " + username;
        
        return String.format("<%s> [%s] %s: %s",
                channel.getChannelName(),
                timestamp.toLocalTime().toString(),
                username,
                messageText
        );
    }
}
