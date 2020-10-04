package chadchat.domain.Channel;

import chadchat.domain.User.User;

import java.util.Objects;

public class Channel {

    private final int id;
    private final String channelName;
    private final User owner;


    public Channel(int id, String channelName, User owner) {
        this.id = id;
        this.channelName = channelName;
        this.owner = owner;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return id == channel.id &&
                Objects.equals(channelName, channel.channelName);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, channelName);
    }
    
    public int getId() {
        return id;
    }

    public String getChannelName() {
        return channelName;
    }
    
    public int getOwnerId() {
        return owner.getId();
    }
    
    @Override
    public String toString() {
        return String.format("[%d] %s", id, channelName);
    }
}
