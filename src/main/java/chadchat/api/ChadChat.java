package chadchat.api;

import chadchat.domain.Message.Message;
import chadchat.domain.User.User;
import chadchat.infrastructure.Database;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChadChat {
    private final Set<MessageObserver> messageObservers = new HashSet<>();
    private final Database db = new Database();
    
    public User userLogin(String username){
        return db.checkLogin(username);
    }
    
    public void createMessage(User user, String msg) {
        // Create message correctly.
        synchronized (this) {
            db.createMessage(msg,user);
            for (MessageObserver messageObserver : messageObservers) {
                messageObserver.notifyNewMessages();
            }
        }
    }
    
    public Iterable<Message> getNewMessages() {
        // Database get messages
        return db.findAllMessages();
    }
    
    public synchronized void registerMessageObserver(MessageObserver observer) {
        messageObservers.add(observer);
    }
    
    
    public interface MessageObserver {
        void notifyNewMessages();
    }
    
}