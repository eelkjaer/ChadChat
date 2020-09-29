package chadchat.domain.Subscription;

import chadchat.domain.Channel.Channel;

import java.time.LocalDateTime;

public class Subscription {

    Channel channel;
    Boolean subcribe;
    // UserRights rights;
    LocalDateTime timestamp;


    public Subscription(Channel channel, boolean Subscribe, LocalDateTime timestamp) {
        // this.message = message;
        this.channel = channel;
        // this.rights = rights;
        this.timestamp = timestamp;
    }

    /*
    public static enum UserRights {
        ALL,
        LIMITED
    }
     */

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Boolean getSubcribe() {
        return subcribe;
    }

    public void setSubcribe(Boolean subcribe) {
        this.subcribe = subcribe;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

