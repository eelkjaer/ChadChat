package chadchat.domain.Message;

import chadchat.domain.Channel.Channel;
import chadchat.domain.User.User;

public interface MessageFactory {
    Message createMessage(User user, Message message);
}
