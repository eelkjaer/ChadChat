package chadchat.domain;

public interface MessageRepository {
    Iterable<Message> findAllMessages();
    Message createMessage(Message message);
}
