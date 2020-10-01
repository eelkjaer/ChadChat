package chadchat.domain.User;

public class UserExists extends Exception{
    public UserExists(){
        super("User already exists");
    }
    public UserExists(int id){
        super("User already exists with id: " + id);
    }
}