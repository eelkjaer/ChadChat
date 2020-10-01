package chadchat.domain.User;

public interface UserRepository extends UserFactory {
    Iterable<User> findAllUsers(String[] userName);
    //ArrayList<User> getAllUsers(String roomName);
    User getUser(String name);
}