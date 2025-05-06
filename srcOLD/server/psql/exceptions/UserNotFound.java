package server.psql.exceptions;

public class UserNotFound extends Exception {
    public UserNotFound(){
        super("User does not exist");
    }
}
