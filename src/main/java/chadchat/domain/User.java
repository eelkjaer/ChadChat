package chadchat.domain;

import java.sql.Timestamp;

public class User {
    private final int id;
    private final String name;
    private final Timestamp timestamp;

    public User(int id, String name, Timestamp timestamp) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + timestamp +
                '}';
    }
}
