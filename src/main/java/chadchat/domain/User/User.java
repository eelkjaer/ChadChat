package chadchat.domain.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

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
        return "{" +
                "id=" + id +
                ", User name='" + userName + '\'' +
                ", createdAt=" + timestamp +
                '}';
    }
}
