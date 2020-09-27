package chadchat.domain;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Message {
    int id;
    String messageText;
    Timestamp timestamp;
    String user;

    public Message(int id, String messageText, Timestamp timestamp, String user) {
        this.id = id;
        this.messageText = messageText;
        this.timestamp = timestamp;
        this.user = user;
    }

    public static Message createMessage(String messageText, Timestamp timestamp, String user) {
        return new Message(-1, messageText, timestamp, user);
    }

    public Message withId(int id) {
        return new Message(id, this.messageText, timestamp, user);
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getMessageText() {
        return messageText;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

}
