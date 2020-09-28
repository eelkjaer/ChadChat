package chadchat.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class User {
    private final int id;
    private final String userName;
    private final LocalDateTime timestamp;


    public User(int id, String userName, LocalDateTime timestamp) {
        this.id = id;
        this.userName = userName;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getTimestamp() {
         return timestamp;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                ", createdAt=" + timestamp +
                '}';
    }
}
