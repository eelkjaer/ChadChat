package chadchat.domain.Message;

import chadchat.domain.User.User;

public interface MessageFactory {
    Message createMessage(String messageText, User user);
}
