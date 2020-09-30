package chadchat.domain;

import java.sql.Timestamp;

public class Subsrcibtion {

    Message message;
    Channel channel;
    UserRights rights;
    Timestamp timestamp;


    public Subsrcibtion(Message message, Channel channel, UserRights rights, Timestamp timestamp) {
        this.message = message;
        this.channel = channel;
        this.rights = rights;
        this.timestamp = timestamp;
    }

    public static enum UserRights {
        ALL,
        LIMITED
    }

    public Message getMessage() {
        return message;
    }

    public Channel getChannel() {
        return channel;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public UserRights getRights() {
        return rights;
    }
}

