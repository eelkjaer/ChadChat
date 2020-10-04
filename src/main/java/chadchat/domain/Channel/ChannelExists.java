package chadchat.domain.Channel;

public class ChannelExists extends Exception{
    public ChannelExists(){
        super("Channel already exists");
    }
    
    public ChannelExists(int id){
        super("Channel already exists with id: " + id);
    }
}
