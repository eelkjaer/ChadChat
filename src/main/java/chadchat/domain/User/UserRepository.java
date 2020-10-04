package chadchat.domain.User;

public interface UserRepository extends UserFactory {
    Iterable<User> findAllUsers(String[] userName);
    User getUser(String name);
}