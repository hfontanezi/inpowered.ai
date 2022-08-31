package ia.inpowered.exception;

public class InvalidContactException extends RuntimeException{
    public InvalidContactException(String message) {
        super("Invalid contact : " + message);
    }
}
