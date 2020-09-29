package chadchat.domain.Message;

public class MessageExists extends Exception{
    public MessageExists(){
        super("Message already exists");
    }
    
    public MessageExists(int id){
        super("Message already exists with id: " + id);
    }
}
