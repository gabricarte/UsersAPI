package personal.project.users.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String parameter){
        super("User " + parameter + " not found");
    }
}
