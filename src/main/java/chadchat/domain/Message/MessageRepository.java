package chadchat.domain.Message;

public interface MessageRepository extends MessageFactory{
    Iterable<Message> findAllMessages();
}
