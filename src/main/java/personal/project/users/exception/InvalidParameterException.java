package personal.project.users.exception;

public class InvalidParameterException extends RuntimeException{
    public InvalidParameterException(String parameter) {
        super(parameter + " is invalid");
    }
}
