package chadchat.api;

import chadchat.domain.Message.Message;
import chadchat.domain.User.User;
import chadchat.infrastructure.Database;

import java.util.HashSet;
import java.util.Set;

public class ChadChat {
    private final Set<MessageObserver> messageObservers = new HashSet<>();
    private final Set<User> activeUsers = new HashSet<>();
    private final Database db = new Database();
    
    public User userLogin(String username){
        return db.checkLogin(username);
    }
    
    public void createMessage(User user, String msg) {
        db.createMessage(msg,user);
        synchronized (this) {
            for (MessageObserver messageObserver : messageObservers) {
                messageObserver.notifyNewMessages();
            }
        }
    }
    
    public Iterable<Message> getNewMessages(int lastSeenMsg) {
        // Database get messages
        return db.findAllMessages(lastSeenMsg);
    }
    
    public synchronized void registerMessageObserver(MessageObserver observer) {
        messageObservers.add(observer);
    }
    
    
    public interface MessageObserver {
        void notifyNewMessages();
    }
    
}