package chadchat.domain.User;

import chadchat.domain.Message.Message;

import java.time.LocalDateTime;

public interface UserFactory {
    User createUser(User user, byte[] salt, byte[] secret) throws UserExists;
}