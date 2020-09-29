package chadchat.domain.Message;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Message {
    private final int id;
    private final String messageText;
    private final LocalDateTime timestamp;
    // private final User user;

    public Message(int id, String messageText, LocalDateTime timestamp) {
        this.id = id;
        this.messageText = messageText;
        this.timestamp = timestamp;
        // this.user = user;
    }
    
    public Message(String messageText){
        this.id = -1;
        this.messageText = messageText;
        this.timestamp = null;
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
    
    

/*
    public User getUser() {
        return user;
    }

 */

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", Message messageText='" + messageText + '\'' +
                ", timestamp=" + timestamp +
                // ", user=" + user +
                '}';
    }
}
