package chadchat.domain.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class User {
    private final int id;
    private final String userName;
    private final LocalDateTime timestamp;
    private final boolean admin;


    public User(int id, String userName, LocalDateTime timestamp, boolean admin) {
        this.id = id;
        this.userName = userName;
        this.timestamp = timestamp;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }
    
    public boolean isAdmin() {
        return admin;
    }
    
    public String getUserName() {
        return userName;
    }

    public LocalDateTime getTimestamp() {
         return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", timestamp=" + timestamp +
                ", admin=" + admin +
                '}';
    }
}
