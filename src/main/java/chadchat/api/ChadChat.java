package chadchat.api;

import chadchat.domain.Message.Message;
import chadchat.domain.User.InvalidPassword;
import chadchat.domain.User.User;
import chadchat.entries.Client;
import chadchat.infrastructure.Database;

import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChadChat {
    private final Set<MessageObserver> messageObservers = new HashSet<>();
    private final Set<User> activeUsers = new HashSet<>();
    private final Database db = new Database();
    private final Set<User> blocked = new HashSet<>();

    public User userLogin(String username, String password) throws InvalidPassword {
        User user = db.checkLogin(username);
        
        if (!user.checkPassword(password)) {
            throw new InvalidPassword();
        }
        
        synchronized (this) {
            activeUsers.add(user);
        }
        return user;
    }
    

    public User createUser(String userName, String password) {
        byte[] salt = User.generateSalt();
        User user = db.createUser(userName, salt, User.calculateSecret(salt, password));
        try {
            return userLogin(user.getUserName(), password);
        } catch (InvalidPassword invalidPassword) {
            throw new RuntimeException(invalidPassword);
        }
    }
    
    public void notifyNewUser(User user){
        String msg = String.format("%s just joined ChadChat®. Say hi!",
                user.getUserName());
    
        createMessage(new User(1,
                "SYSTEM",
                null,
                true, null, null ),
                msg);
    }

   
    public synchronized void logout(User user, Socket socket) {
        try {
            activeUsers.remove(user);
            socket.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
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