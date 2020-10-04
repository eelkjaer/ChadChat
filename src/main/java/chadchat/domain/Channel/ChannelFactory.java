package chadchat.domain.Channel;

import chadchat.domain.User.User;

public interface ChannelFactory {
    Channel createChannel(String channelName, User owner);
}
