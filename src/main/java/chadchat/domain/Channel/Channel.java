package chadchat.domain.Channel;

import java.time.LocalDateTime;

public class Channel {

    private final int id;
    private final String channelName;
    // private final LocalDateTime timestamp;


    public Channel(int id, String channelName) {
        this.id = id;
        this.channelName = channelName;
        // this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getChannelName() {
        return channelName;
    }

    // public LocalDateTime getTimestamp() {
       //  return timestamp;
    // }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", Channel name='" + channelName + '\'' +
                // ", createdAt=" + timestamp +
                '}';
    }
}
