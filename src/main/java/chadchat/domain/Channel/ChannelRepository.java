package chadchat.domain.Channel;

public interface ChannelRepository extends ChannelFactory {

    Iterable<Channel> findAllChannels(int id);
    //ArrayList<User> getAllUsers(String roomName);
    // Channel getChannel(String name);
}
