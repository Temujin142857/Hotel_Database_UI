package backend;

public class UnauthorisedAccessException extends Exception{
    public UnauthorisedAccessException(String message){
        super(message);
    }
}
