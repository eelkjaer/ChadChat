package chadchat.api;

import chadchat.domain.Message.Message;
import chadchat.domain.User.User;
import chadchat.infrastructure.Database;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChadChat {
    private final Set<MessageObserver> messageObservers = new HashSet<>();
    private final Set<User> activeUsers = new HashSet<>();
    private final Database db = new Database();
    private final Set<User> blocked = new HashSet<>();

    public User userLogin(String username) {
        User user = db.checkLogin(username);
        synchronized (this) {
            activeUsers.add(user);
        }
        return user;
    }

    public synchronized void logout(User user) {
        activeUsers.remove(user);
    }
    
    public synchronized void setBlocked(User user, User admin) {
        blocked.add(user);
        for (var ob: messageObservers) {
            ob.notifyUserBlocked();
        }
        String blockedMsg = String.format("%s was kicked by %s",
                user.getUserName(),
                admin.getUserName());
        
        createMessage(new User(1,
                "SYSTEM",
                null,
                true, null, null ),blockedMsg);
    }
    
    public synchronized void removeBlocked(User user) {
        blocked.remove(user);
    }
    
    public synchronized boolean isUserBlocked(User user) {
        return blocked.contains(user);
    }
    
    public Iterable<User> getBlockedUsers() {
        return blocked;
    }
    
    public User findActiveUser(String username){
        for(User u: activeUsers){
            if(u.getUserName().equalsIgnoreCase(username)){
                return u;
            }
        }
        return null;
    }
    

    public void createMessage(User user, String msg) {
        db.createMessage(msg, user);
        synchronized (this) {
            for (MessageObserver messageObserver : messageObservers) {
                messageObserver.notifyNewMessages();
            }
        }
    }

    public Iterable<Message> getNewMessages(int lastSeenMsg) {
        return db.findAllMessages(lastSeenMsg);
    }

    public synchronized void registerMessageObserver(MessageObserver observer) {
        messageObservers.add(observer);
    }

    public synchronized Iterable<User> getActiveUsers() {
        return List.copyOf(activeUsers);
    }


    public interface MessageObserver {
        void notifyNewMessages();
        void notifyUserBlocked();
    }

}