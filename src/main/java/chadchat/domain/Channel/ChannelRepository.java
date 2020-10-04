package chadchat.domain.Channel;

public interface ChannelRepository extends ChannelFactory {

    Iterable<Channel> findAllChannels(int id);
    Channel getChannel(String name);
}
